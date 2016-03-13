package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Notes(
                 id: Int,
                 note: String
               )

object Notes {

  private[mine] lazy val raw = getDataset("notes.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Notes] = Try {
    raw match {
      case List(id, note) =>
        Notes(
          id.toInt,
          note
        )
    }
  }

}
