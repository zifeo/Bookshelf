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
      case List(id, authorId, publisherId, url, publicationSeriesId, titleId, awardTypeId, titleSeriesId, awardCategoryId) =>
        Webpages(
          id.toInt,
          intOrNone(authorId),
          intOrNone(publisherId),
          intOrNone(titleId),
          url,
          intOrNone(publicationSeriesId),
          intOrNone(awardTypeId),
          intOrNone(titleSeriesId),
          intOrNone(awardCategoryId)
        )
    }
  }

}
