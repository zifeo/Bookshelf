package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Publication_content(
                                titleId: Int,
                                publicationId: Int,
                              )

object Publication_content {

  private[mine] lazy val raw = getDataset("publications_content.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Publication_content] = Try {
    raw match {
      case List(id, title, pub) =>
        Publication_content(
          title.toInt,
          pub.toInt
        )
    }
  }

}

