package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class TitlesSeries(
                          id: Int,
                          title: String,
                          parent: Option[Int],
                          noteId: Option[Int]
                        )

object TitlesSeries {

  private[mine] lazy val raw = getDataset("titles_series.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[TitlesSeries] = Try {
    raw match {
      case List(id, title, parent, note_id) =>
        TitlesSeries(
          id.toInt,
          title,
          intOrNone(parent),
          intOrNone(note_id)
        )
    }
  }

}