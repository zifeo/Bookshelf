package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class TitlesSeries(
                         id: Int,
                         title: String,
                         parent: Int,
                         noteId: Option[Int]
                       )

object TitlesSeries {

  def parseCols(raw: List[String]): Try[TitlesSeries] = Try {
    raw match {
      case List(id, title, parent, noteId) =>
        TitlesSeries(
          id.toInt,
          title,
          intOrZero(parent),
          intOrNone(noteId)
        )
    }
  }

}