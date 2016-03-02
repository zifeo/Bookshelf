package bookshelf.mine.publications

import bookshelf.mine._

import scala.util.Try

case class Publisher(
                              publisher_id: Int,
                              publisher_name: String,
                              note_id: Option[Int]
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

