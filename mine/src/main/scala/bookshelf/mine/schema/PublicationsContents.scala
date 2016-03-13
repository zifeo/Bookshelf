package bookshelf.mine.schema

import scala.util.Try

case class PublicationsContents(
                                 titleId: Int,
                                 publicationId: Int,
                               )

object PublicationsContents {

  def parseCols(raw: List[String]): Try[PublicationsContents] = Try {
    raw match {
      case List(id, title, pub) =>
        PublicationsContents(
          title.toInt,
          pub.toInt
        )
    }
  }

}

