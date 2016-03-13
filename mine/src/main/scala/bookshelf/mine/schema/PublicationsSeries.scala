package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class PublicationsSeries(
                               id: Int,
                               name: String,
                               noteId: Option[Int]
                             )

object PublicationsSeries {

  def parseCols(raw: List[String]): Try[PublicationsSeries] = Try {
    raw match {
      case List(id, title, pub) =>
        PublicationsSeries(
          id.toInt,
          title,
          intOrNone(pub)
        )
    }
  }

}

