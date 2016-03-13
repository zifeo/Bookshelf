package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Publisher(
                              id: Int,
                              name: String,
                              noteId: Option[Int]
                            )

object Publisher {

  private[mine] lazy val raw = getDataset("publishers.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Publisher] = Try {
    raw match {
      case List(id, name, note) =>
        Publisher(
          id.toInt,
          name,
          intOrNone(note)
        )
    }
  }

}

