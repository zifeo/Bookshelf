package bookshelf

import java.text.SimpleDateFormat

import scala.util.Try

package object mine {

  /**
    * Parse an integer as an option.
    *
    * @param raw number
    * @return some of integer or none
    */
  def intOrNone(raw: String): Option[Int] =
    Try(raw.toInt).toOption

  /**
    * Parse a long integer as an option.
    *
    * @param raw number
    * @return some of long integer or none
    */
  def longOrNone(raw: String): Option[Long] =
    Try(raw.toLong).toOption

  private val FORMAT_DATE_1 = new SimpleDateFormat("yyyy-MM-dd")
  private val FORMAT_DATE_2 = new SimpleDateFormat("dd/MM/yy")
  private val firstACDate = new java.util.Date()

  def stringToDate(raw: String): java.util.Date = {

    // sql format cannot handle BC
    def requireAC(date: java.util.Date): java.util.Date = {
      if (date.compareTo(firstACDate) < 0) {
        throw new Exception("date cannot be BC")
      }
      date
    }

    if (raw.contains("-")) {
      FORMAT_DATE_1.parse(raw)
    } else if (raw.contains("/")) {
      FORMAT_DATE_2.parse(raw)
    } else {
      throw new Exception(s"cannot parse $raw to date")
    }
  }

  val REGEX_PAGES_1 = "([\\d|+]*)([A-z]*)([\\d|+]*)\\[(\\d*)\\]([\\d|+]*)".r
  val REGEX_PAGES_2 = "([\\d|+]*)\\[(\\d*)\\]([\\d|+]*)([A-z]*)([\\d|+]*)".r
  val REGEX_PAGES_3 = "([\\d|+]*)\\[(\\d*)\\]([\\d|+]*)".r
  val REGEX_PAGES_4 = "([\\d|+]*)([A-z]*)([\\d|+]*)".r

  def getPages(raw: String): (Option[Int], Option[Int]) = {
    def impl(raw: String): (String, String) = raw match {
      case REGEX_PAGES_1(g1, g2, g3, g4, g5) => (g1 + g3 + g5, (toArabic(g2) + g4.toInt).toString)
      case REGEX_PAGES_2(g1, g2, g3, g4, g5) => (g1 + g3 + g5, (g2.toInt + toArabic(g4)).toString)
      case REGEX_PAGES_3(g1, g2, g3) => (g1 + g3, g2)
      case REGEX_PAGES_4(g1, g2, g3) => (g1 + g3, toArabic(g2).toString)
      case x => (x, "")
    }

    def numberOrEmpty(num: String): Int =
      if (num.isEmpty) 0
      else num.toInt

    val (pagesLeft, pagesRight) = impl(raw)

    val leftRes = Try {
      pagesLeft.split('+').map(numberOrEmpty).sum
    }.toOption

    val rightRes = Try {
      numberOrEmpty(pagesRight)
    }.toOption

    (leftRes, rightRes)
  }

  private def toArabic(number: String): Int = {
    def impl(digits: List[Char]): Int = digits match {
      case Nil => 0
      case 'M' :: xs => 1000 + impl(xs)
      case 'C' :: 'M' :: xs => 900 + impl(xs)
      case 'D' :: xs => 500 + impl(xs)
      case 'C' :: 'D' :: xs => 400 + impl(xs)
      case 'C' :: xs => 100 + impl(xs)
      case 'X' :: 'C' :: xs => 90 + impl(xs)
      case 'L' :: xs => 50 + impl(xs)
      case 'X' :: 'L' :: xs => 40 + impl(xs)
      case 'X' :: xs => 10 + impl(xs)
      case 'I' :: 'X' :: xs => 9 + impl(xs)
      case 'V' :: xs => 5 + impl(xs)
      case 'I' :: 'V' :: xs => 4 + impl(xs)
      case 'I' :: xs => 1 + impl(xs)
      case _ :: xs => impl(xs)
    }

    impl(number.toUpperCase().toList)
  }

  def stringOrNone(raw: String): Option[String] = {
    raw match {
      case "\\N" | "" => None
      case x => Some(x)
    }
  }

  def parseBoolean(str: String): Boolean = str.toLowerCase match {
    case "true" | "1" | "yes" => true
    case "false" | "0" | "false" => false
    case _ => throw new Exception(s"cannot parse $str to boolean")
  }


}
