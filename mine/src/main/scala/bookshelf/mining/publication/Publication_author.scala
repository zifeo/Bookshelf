package bookshelf.mining.publication

import bookshelf.mining._

import scala.util.Try

case class Publication_author(
                               pa_id: Int,
                               publication_id: Int,
                               author_id: Int
                             )

object Publication_author {

  val raw = getDataset("publications_authors.csv")
  val all = raw.map(parseCols)

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

