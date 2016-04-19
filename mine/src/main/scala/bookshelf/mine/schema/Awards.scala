package bookshelf.mine.schema

import java.util.Date
import bookshelf.mine._

import scala.util.Try

case class Awards(
                   id: Int,
                   title: String,
                   date: Date,
                   categoryId: Int,
                   typeId: Int,
                   noteId: Option[Int]
                 )

object Awards {

  def parseCols(raw: List[String]): Try[Awards] = Try {
    raw match {
      case List(id, title, date, typeCode, typeId, categoryId, noteId) =>
        Awards(
          id.toInt,
          title,
          stringToDate(date).get,
          categoryId.toInt,
          typeId.toInt,
          intOrNone(noteId)
        )
    }
  }

}
