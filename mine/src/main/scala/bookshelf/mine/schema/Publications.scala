package bookshelf.mine.schema

import bookshelf.mine._
import org.joda.time.DateTime

import scala.util.Try

case class Publications(
                         id: Int,
                         title: Option[String],
                         datePub: DateTime,
                         publisherId: Option[Int],
                         pages: Option[Int],
                         preface: Option[Int],
                         packaging_type: Option[String],
                         `type`: Option[String],
                         isbn: Option[Long],
                         cover: Option[String],
                         price: Option[Double],
                         currency: Option[String],
                         noteId: Option[Int],
                         pub_series_id: Option[Int],
                         pub_series_num: Option[Int]
                       )

object Publications {

  val types = List("ANTHOLOGY", "COLLECTION", "MAGAZINE", "NONFICTION", "NOVEL", "OMNIBUS", "FANZINE", "CHAPBOOK")
    .map(_.toLowerCase)

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
          stringOrNone(title),
          stringToDate(date), // need to manage the litteral without -
          intOrNone(publisherId),
          book_pages,
          pages_prefaces,
          stringOrNone(packaging),
          requireIn(pubType, types),
          longOrNone(isbn),
          stringOrNone(image),
          money,
          stringOrNone(currency),
          intOrNone(noteId),
          intOrNone(pubSeriesId),
          intOrNone(pubSeriesNb)
        )
    }
  }

}

