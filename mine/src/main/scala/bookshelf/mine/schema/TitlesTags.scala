package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class TitlesTags(
                       tagId: Int,
                       titleId: Int
                     )

object TitlesTags {

  private[mine] lazy val raw = getDataset("titles_tag.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[TitlesTags] = Try {
    raw match {
      case List(id, tag, title) =>
        TitlesTags(
          tag.toInt,
          title.toInt
        )
    }
  }
}

