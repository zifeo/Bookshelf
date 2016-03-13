package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Titles_tag(
                       tagId: Int,
                       titleId: Int
                     )

object Titles_tag {

  private[mine] lazy val raw = getDataset("titles_tag.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Titles_tag] = Try {
    raw match {
      case List(id, tag, title) =>
        Titles_tag(
          tag.toInt,
          title.toInt
        )
    }
  }
}

