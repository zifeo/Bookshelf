package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class PublicationsAuthors(
                               publicationId: Int,
                               authorId: Int
                             )

object PublicationsAuthors {

  private[mine] lazy val raw = getDataset("publications_authors.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[PublicationsAuthors] = Try {
    raw match {
      case List(id, pub, auth) =>
        PublicationsAuthors(
          pub.toInt,
          auth.toInt
        )
    }
  }

}

