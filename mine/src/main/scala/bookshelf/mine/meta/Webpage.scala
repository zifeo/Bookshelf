package bookshelf.mine.meta

import bookshelf.mine._

import scala.util.Try

case class Webpage(
                    id: Int,
                    author_id: Option[Int],
                    publisher_id: Option[Int],
                    URL: String,
                    publication_series_id: Option[Int],
                    title_id: Option[Int],
                    award_type_id: Option[Int],
                    title_series_id: Option[Int],
                    award_category_id: Option[Int]
                  )

object Webpage {

  private[mine] lazy val raw = getDataset("webpages.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Webpage] = Try {
    raw match {
      case List(id, author_id, publisher_id, url, publication_series_id, title_id, award_type_id, title_series_id, award_category_id) =>
        Webpage(
          id.toInt,
          intOrNone(author_id),
          intOrNone(publisher_id),
          url,
          intOrNone(publication_series_id),
          intOrNone(title_id),
          intOrNone(award_type_id),
          intOrNone(title_series_id),
          intOrNone(award_category_id)
        )
    }
  }

}
