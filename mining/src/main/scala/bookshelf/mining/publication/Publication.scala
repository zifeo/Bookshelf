package bookshelf.mining.publication

import java.text.SimpleDateFormat

import bookshelf.mining._
import bookshelf.mining.publication.Publication_Type.Publication_Type

import scala.util.Try

object Publication_Type extends Enumeration {
  type Publication_Type = Value
  val ANTHOLOGY = Value("ANTHOLOGY")
  val BACKCOVERART = Value("BACKCOVERART")
  val COLLECTION = Value("COLLECTION")
  val COVERART = Value("COVERART")
  val INTERIORART = Value("INTERIORART")
  val EDITOR = Value("EDITOR")
  val ESSAY = Value("ESSAY")
  val INTERVIEW = Value("INTERVIEW")
  val NOVEL = Value("NOVEL")
}

object Currency extends Enumeration {
  type Currency = Value
  val EURO = Value("€")
  val DOLLAR = Value("$")
  val POUND = Value("£")
  val FR_CH = Value("CH")
}

case class Publications(
                         id: Int,
                         title: String,
                         date: java.util.Date,
                         publisher_id: Option[Int],
                         pages: Option[(Int, Int)], // book and preface
                         packaging: String,
                         pub_type: Option[Publication_Type],
                         isbn: Option[Long],
                         image: String,
                         price: Option[(Double, String)], // amount and currency
                         note_id: Option[Int],
                         pub_series_id: Option[Int],
                         pub_series_nb: Option[Int]
                       )

object Publications {

  val PATTERN_PAGES_1 = "(\\d*)\\+([A-z]*)".r
  val PATTERN_PAGES_2 = "([A-z]*)\\+(\\d*)".r
  val PATTERN_PAGES_3 = "(?:\\[(\\d)\\]*)\\+(\\d*)".r
  val PATTERN_PAGES_4 = "(\\d*)\\+(?:\\[(\\d)\\]*)".r

  val PATTERN_MONEY_1 = "([\\d|\\.]*)([\\D]|\\.]*)".r
  val PATTERN_MONEY_2 = "([\\D]|\\.]*)([\\d|\\.]*)".r

  val raw = getDataset("publications.csv")
  val all = raw.map(parseCols)

  def getPages(pages: String): Option[(Int, Int)] = Try(pages match {
    case PATTERN_PAGES_1(g1, g2) => (g1.toInt, toArabic(g2))
    case PATTERN_PAGES_2(g1, g2) => (g2.toInt, toArabic(g1))
    case PATTERN_PAGES_3(g1, g2) => (g2.toInt, g1.toInt)
    case PATTERN_PAGES_4(g1, g2) => (g1.toInt, g2.toInt)
    case _ => (pages.toInt, 0)
  }).toOption

  def getCurrency(money: String): Option[(Double, String)] = Try(money match {
    case PATTERN_MONEY_1(g1, g2) => (g1.toDouble, g2)
    case PATTERN_MONEY_2(g1, g2) => (g2.toDouble, g1)
    case _ => (money.toDouble, "")
  }).toOption

  def parseCols(raw: List[String]): Try[Publications] = Try {
    raw match {
      case List(id, title, date, publisher_id, pages, packaging, pub_type, isbn, image, price, note_id, pub_series_id, pub_series_nb) =>
        Publications(
          id.toInt,
          title,
          stringToDate(date), // need to manage the litteral without -
          intOrNone(publisher_id),
          getPages(pages),
          packaging,
          Publication_Type.values.find(_.equals(pub_type)),
          longOrNone(isbn),
          image,
          getCurrency(price),
          intOrNone(note_id),
          intOrNone(pub_series_id),
          intOrNone(pub_series_nb)
        )
    }
  }

}

