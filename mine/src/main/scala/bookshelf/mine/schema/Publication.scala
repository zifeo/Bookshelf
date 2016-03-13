package bookshelf.mine.schema

import bookshelf.mine._
import bookshelf.mine.schema.Publication.Publication_Type.Publication_Type

import scala.util.Try

case class Publication(
                         id: Int,
                         title: String,
                         datePub: java.util.Date,
                         publisherId: Option[Int],
                         pages: Option[Int],
                         preface: Option[Int],
                         packaging: String,
                         `type`: Option[Publication_Type],
                         isbn: Option[Long],
                         image: String,
                         price: Option[Double],
                         currency: String,
                         noteId: Option[Int],
                         pubSeriesId: Option[Int],
                         pubSeriesNb: Option[Int]
                       )

object Publication {

  object Publication_Type extends Enumeration {
    type Publication_Type = Value
    val ANTHOLOGY = Value("ANTHOLOGY")
    val COLLECTION = Value("COLLECTION")
    val MAGAZINE = Value("MAGAZINE")
    val NONFICTION = Value("NONFICTION")
    val NOVEL = Value("NOVEL")
    val OMNIBUS = Value("OMNIBUS")
    val FANZINE = Value("FANZINE")
    val CHAPBOOK = Value("CHAPBOOK")
  }

  object Currency extends Enumeration {
    type Currency = Value
    val EURO = Value("€")
    val DOLLAR = Value("$")
    val POUND = Value("£")
    val FR_CH = Value("CH")
  }

  val PATTERN_MONEY_1 = "([\\d|\\.]*)([\\D]|\\.]*)".r
  val PATTERN_MONEY_2 = "([\\D]|\\.]*)([\\d|\\.]*)".r

  private[mine] lazy val raw = getDataset("publications.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def getCurrency(money: String): (Option[Double], String) = money match {
    case PATTERN_MONEY_1(g1, g2) => (Try(g1.toDouble).toOption, g2)
    case PATTERN_MONEY_2(g1, g2) => (Try(g2.toDouble).toOption, g1)
    case _ => (Try(money.toDouble).toOption, "")
  }

  def parseCols(raw: List[String]): Try[Publication] = Try {
    raw match {
      case List(id, title, date, publisher_id, pages, packaging, pub_type, isbn, image, price, note_id, pub_series_id, pub_series_nb) => {
        val (book_pages, pages_prefaces) = getPages(pages)
        val (money, currency) = getCurrency(price)
        Publication(
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

