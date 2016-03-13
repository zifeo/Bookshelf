package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Publishers(
                              id: Int,
                              name: String,
                              noteId: Option[Int]
                            )

object Publishers {

  private[mine] lazy val raw = getDataset("publishers.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Publishers] = Try {
    raw match {
      case List(id, name, note) =>
        Publishers(
          id.toInt,
          name,
          intOrNone(note)
        )
    }
  }

}

