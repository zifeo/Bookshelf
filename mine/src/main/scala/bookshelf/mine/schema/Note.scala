package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Note(
                 id: Int,
                 note: String
               )

object Note {

  private[mine] lazy val raw = getDataset("notes.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Note] = Try {
    raw match {
      case List(id, note) =>
        Note(
          id.toInt,
          note
        )
    }
  }

}
