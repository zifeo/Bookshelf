package bookshelf.mine.schema

import scala.util.Try

case class Notes(
                  id: Int,
                  note: String
                )

object Notes {

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
