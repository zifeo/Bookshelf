package bookshelf.mining.title

import bookshelf.mining._

import scala.util.Try

case class Titles_series(
                          id: Int,
                          title: String,
                          parent: Option[Int],
                          note_id: Option[Int]
                        )

object Titles_series {
  val raw = getDataset("titles_series.csv")
  val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Titles_series] = Try {
    raw match {
      case List(id, title, parent, note_id) =>
        Titles_series(
          id.toInt,
          title,
          intOrNone(parent),
          intOrNone(note_id)
        )
    }
  }
}