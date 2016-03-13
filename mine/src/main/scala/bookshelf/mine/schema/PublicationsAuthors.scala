package bookshelf.mine.schema

import scala.util.Try

case class PublicationsAuthors(
                                publicationId: Int,
                                authorId: Int
                              )

object PublicationsAuthors {

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

