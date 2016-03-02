package bookshelf

import java.text.SimpleDateFormat

import bookshelf.mine._

import scala.io.{Codec, Source}
import scala.util.Try
import scala.util.matching.Regex

package object mine {

  /**
    * Returns the given file as lists of lists.
    *
    * @param name file name
    * @return rows of columns
    */
  private[mine] def getDataset(name: String): List[List[String]] =
    Source.fromFile(s"./datasets/$name")(Codec.ISO8859).getLines().map(_.split('\t').toList).toList

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

  def stringToDate(raw: String): java.util.Date = {
    if (raw.contains("-")) {
      FORMAT_DATE_1.parse(raw)
    }
    else if (raw.contains("/")) {
      FORMAT_DATE_2.parse(raw)
    } else {
      if (true) {
        // above if the is a full integer, so we can convert it and therefore it is the number of days after y. 1970
        new java.util.Date()
      } else {
        new java.util.Date()
      }
    }
  }

  val REGEX_PAGES_1 = "([\\d|+]*)([A-z]*)([\\d|+]*)\\[(\\d*)\\]([\\d|+]*)".r
  val REGEX_PAGES_2 = "([\\d|+]*)\\[(\\d*)\\]([\\d|+]*)([A-z]*)([\\d|+]*)".r
  val REGEX_PAGES_3 = "([\\d|+]*)\\[(\\d*)\\]([\\d|+]*)".r
  val REGEX_PAGES_4 = "([\\d|+]*)([A-z]*)([\\d|+]*)".r


  def getPages(raw: String): (Option[Int], Option[Int]) = {
    val pages = inner(raw)
    (Try(pages._1.split('+').toList.map(x => if (x.isEmpty) 0 else x.toInt).sum).toOption, Try(if (pages._2.isEmpty) 0 else pages._2.toInt).toOption)
  }

  private def inner(raw: String): (String, String) = raw match {
    case REGEX_PAGES_1(g1, g2, g3, g4, g5) => (g1 + g3 + g5, (toArabic(g2) + g4.toInt).toString)
    case REGEX_PAGES_2(g1, g2, g3, g4, g5) => (g1 + g3 + g5, (g2.toInt + toArabic(g4)).toString)
    case REGEX_PAGES_3(g1, g2, g3) => (g1 + g3, g2)
    case REGEX_PAGES_4(g1, g2, g3) => (g1 + g3, toArabic(g2).toString)
    case x => (x, "")
  }

  private def toArabic(number: String): Int = toArabic(number.toUpperCase().toList)

  private def toArabic(digits: List[Char]): Int = digits match {
    case Nil => 0
    case 'M' :: xs => 1000 + toArabic(xs)
    case 'C' :: 'M' :: xs => 900 + toArabic(xs)
    case 'D' :: xs => 500 + toArabic(xs)
    case 'C' :: 'D' :: xs => 400 + toArabic(xs)
    case 'C' :: xs => 100 + toArabic(xs)
    case 'X' :: 'C' :: xs => 90 + toArabic(xs)
    case 'L' :: xs => 50 + toArabic(xs)
    case 'X' :: 'L' :: xs => 40 + toArabic(xs)
    case 'X' :: xs => 10 + toArabic(xs)
    case 'I' :: 'X' :: xs => 9 + toArabic(xs)
    case 'V' :: xs => 5 + toArabic(xs)
    case 'I' :: 'V' :: xs => 4 + toArabic(xs)
    case 'I' :: xs => 1 + toArabic(xs)
    case _ :: xs => toArabic(xs)
  }

}
