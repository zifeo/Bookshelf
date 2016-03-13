package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Awards(
                  id: Int,
                  title: String,
                  date: java.util.Date,
                  categoryId: Int,
                  noteId: Option[Int]
                )

object Awards {

  private[mine] lazy val raw = getDataset("awards.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Awards] = Try {
    raw match {
      case List(id, title, date, type_code, type_id, category_id, note_id) =>
        Awards(
          id.toInt,
          title,
          stringToDate(date),
          category_id.toInt,
          intOrNone(note_id)
        )
    }
  }

}
