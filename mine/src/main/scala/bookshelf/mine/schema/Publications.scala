package bookshelf.mine.schema

import java.util.Date

import bookshelf.mine._

import scala.util.Try

case class Publications(
                         id: Int,
                         title: String,
                         datePub: Date,
                         publisherId: Option[Int],
                         pages: Option[Int],
                         prefacePages: Option[Int],
                         packagingType: Option[String],
                         `type`: Option[String],
                         isbn: Option[Long],
                         cover: Option[String],
                         price: Option[Double],
                         currency: Option[String],
                         noteId: Option[Int],
                         pubSeriesId: Option[Int],
                         pubSeriesNum: Option[Int]
                       )

object Publications {

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
          stringToDate(date).get,
          intOrNone(publisherId),
          book_pages,
          pages_prefaces,
          stringOrNone(packaging),
          requireIn(pubType, PublicationsTypes.values),
          longOrNone(isbn),
          stringOrNone(image),
          money,
          stringOrNone(currency),
          intOrNone(noteId),
          intOrNone(pubSeriesId),
          intOrNone(pubSeriesNb)
        )
      case List(id, title, date, publisherId, pages, packaging, pubType, isbn, image, price, noteId, pubSeriesId) =>
        val (book_pages, pages_prefaces) = getPages(pages)
        val (money, currency) = parseCurrency(price)
        Publications(
          id.toInt,
          title,
          stringToDate(date).get,
          intOrNone(publisherId),
          book_pages,
          pages_prefaces,
          stringOrNone(packaging),
          requireIn(pubType, PublicationsTypes.values),
          longOrNone(isbn),
          stringOrNone(image),
          money,
          stringOrNone(currency),
          intOrNone(noteId),
          intOrNone(pubSeriesId),
          None
        )
    }
  }

}

