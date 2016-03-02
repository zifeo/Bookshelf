package bookshelf.mine.titles

import bookshelf.mine._

import scala.util.Try

case class Titles_awards(
                          taw_id: Int,
                          award_id: Int,
                          title_id: Int
                        )

object Titles_awards {
  val raw = getDataset("titles_awards.csv")
  val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Titles_awards] = Try {
    raw match {
      case List(taw_id, aw, title) =>
        Titles_awards(
          taw_id.toInt,
          aw.toInt,
          title.toInt
        )
    }
  }
}

