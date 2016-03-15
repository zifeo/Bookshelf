package bookshelf.mine.schema

import bookshelf.mine._
import org.joda.time.DateTime

import scala.util.Try

case class Awards(
                   id: Int,
                   title: String,
                   date: DateTime,
                   categoryId: Int,
                   noteId: Option[Int]
                 )

object Awards {

  def parseCols(raw: List[String]): Try[Awards] = Try {
    raw match {
      case List(id, title, date, typeCode, typeId, categoryId, noteId) =>
        Awards(
          id.toInt,
          title,
          stringToDate(date),
          categoryId.toInt,
          intOrNone(noteId)
        )
    }
  }

}
