package bookshelf.mine.schema

import bookshelf.mine._
import org.joda.time.DateTime

import scala.util.Try

case class Publications(
                         id: Int,
                         title: String,
                         datePub: DateTime,
                         publisherId: Option[Int],
                         pages: Option[Int],
                         preface: Option[Int],
                         packaging: String,
                         `type`: Option[String],
                         isbn: Option[Long],
                         image: String,
                         price: Option[Double],
                         currency: String,
                         noteId: Option[Int],
                         pubSeriesId: Option[Int],
                         pubSeriesNb: Option[Int]
                       )

object Publications {

  object Type extends Enumeration {
    type PublicationType = Value
    val ANTHOLOGY = Value("ANTHOLOGY")
    val COLLECTION = Value("COLLECTION")
    val MAGAZINE = Value("MAGAZINE")
    val NONFICTION = Value("NONFICTION")
    val NOVEL = Value("NOVEL")
    val OMNIBUS = Value("OMNIBUS")
    val FANZINE = Value("FANZINE")
    val CHAPBOOK = Value("CHAPBOOK")
  }

  val PATTERN_MONEY_1 = "([\\d|\\.]*)([\\D]|\\.]*)".r
  val PATTERN_MONEY_2 = "([\\D]|\\.]*)([\\d|\\.]*)".r

  def parseCurrency(money: String): (Option[Double], String) = money match {
    case PATTERN_MONEY_1(g1, g2) => (Try(g1.toDouble).toOption, g2)
    case PATTERN_MONEY_2(g1, g2) => (Try(g2.toDouble).toOption, g1)
    case _ => (Try(money.toDouble).toOption, "")
  }

  def parseCols(raw: List[String]): Try[Publications] = Try {
    raw match {
      case List(id, title, date, publisherId, pages, packaging, pubType, isbn, image, price, noteId, pubSeriesId, pubSeriesNb) =>
        val (book_pages, pages_prefaces) = getPages(pages)
        val (money, currency) = parseCurrency(price)
        Publications(
          id.toInt,
          title,
          stringToDate(date), // need to manage the litteral without -
          intOrNone(publisherId),
          book_pages,
          pages_prefaces,
          packaging,
          stringOrNone(Type.withName(pubType).toString),
          longOrNone(isbn),
          image,
          money,
          currency,
          intOrNone(noteId),
          intOrNone(pubSeriesId),
          intOrNone(pubSeriesNb)
        )
    }
  }

}

