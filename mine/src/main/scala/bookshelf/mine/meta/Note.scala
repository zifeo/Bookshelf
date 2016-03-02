package bookshelf.mine

import scala.util.Try

case class Note(
                id: Int,
                note: String
              )

object Note {

  val raw = getDataset("notes.csv")
  val all = raw.map(parseCols)

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
