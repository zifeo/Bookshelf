package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Webpages(
                    id: Int,
                    authorId: Option[Int],
                    publisherId: Option[Int],
                    titleId: Option[Int],
                    url: String,
                    publicationsSeriesId: Option[Int],
                    awardTypeId: Option[Int],
                    titleSeriesId: Option[Int],
                    awardCategoryId: Option[Int]
                  )

object Webpages {

  private[mine] lazy val raw = getDataset("webpages.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Webpages] = Try {
    raw match {
      case List(id, author_id, publisher_id, url, publication_series_id, title_id, award_type_id, title_series_id, award_category_id) =>
        Webpages(
          id.toInt,
          intOrNone(author_id),
          intOrNone(publisher_id),
          intOrNone(title_id),
          url,
          intOrNone(publication_series_id),
          intOrNone(award_type_id),
          intOrNone(title_series_id),
          intOrNone(award_category_id)
        )
    }
  }

}
