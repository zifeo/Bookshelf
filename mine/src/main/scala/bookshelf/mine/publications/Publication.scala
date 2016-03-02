package bookshelf.mine.publications

import java.text.SimpleDateFormat

import bookshelf.mine._
import bookshelf.mine.publications.Publication_Type.Publication_Type

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
                         pages: Option[Int],
                         preface: Option[Int],
                         packaging: String,
                         pub_type: Option[Publication_Type],
                         isbn: Option[Long],
                         image: String,
                         price: Option[Double],
                         currency: String,
                         note_id: Option[Int],
                         pub_series_id: Option[Int],
                         pub_series_nb: Option[Int]
                       )

object Publications {

  val PATTERN_MONEY_1 = "([\\d|\\.]*)([\\D]|\\.]*)".r
  val PATTERN_MONEY_2 = "([\\D]|\\.]*)([\\d|\\.]*)".r

  val raw = getDataset("publications.csv")
  val all = raw.map(parseCols)

  def getCurrency(money: String): (Option[Double], String) = money match {
    case PATTERN_MONEY_1(g1, g2) => (Try(g1.toDouble).toOption, g2)
    case PATTERN_MONEY_2(g1, g2) => (Try(g2.toDouble).toOption, g1)
    case _ => (Try(money.toDouble).toOption, "")
  }

  def parseCols(raw: List[String]): Try[Publications] = Try {
    raw match {
      case List(id, title, date, publisher_id, pages, packaging, pub_type, isbn, image, price, note_id, pub_series_id, pub_series_nb) => {
        val (book_pages, pages_prefaces) = getPages(pages)
        val (money, currency) = getCurrency(price)
        Publications(
          id.toInt,
          title,
          stringToDate(date), // need to manage the litteral without -
          intOrNone(publisher_id),
          book_pages,
          pages_prefaces,
          packaging,
          Publication_Type.values.find(_.equals(pub_type)),
          longOrNone(isbn),
          image,
          money,
          currency,
          intOrNone(note_id),
          intOrNone(pub_series_id),
          intOrNone(pub_series_nb)
        )
      }
    }
  }

}

