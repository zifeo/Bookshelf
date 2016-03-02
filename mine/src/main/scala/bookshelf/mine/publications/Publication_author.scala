package bookshelf.mine.publications

import bookshelf.mine._

import scala.util.Try

case class Publication_author(
                               pa_id: Int,
                               publication_id: Int,
                               author_id: Int
                             )

object Publication_author {

  private[mine] lazy val raw = getDataset("publications_authors.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Publication_author] = Try {
    raw match {
      case List(id, pub, auth) =>
        Publication_author(
          id.toInt,
          pub.toInt,
          auth.toInt
        )
    }
  }

}

