package bookshelf.mine.schema

import scala.util.Try

case class TitlesTags(
                       tagId: Int,
                       titleId: Int
                     )

object TitlesTags {

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

