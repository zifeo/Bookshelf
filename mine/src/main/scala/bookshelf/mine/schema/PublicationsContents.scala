package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class PublicationsContents(
                                titleId: Int,
                                publicationId: Int,
                              )

object PublicationsContents {

  private[mine] lazy val raw = getDataset("publications_content.csv")
  private[mine] lazy val all = raw.map(parseCols)

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

