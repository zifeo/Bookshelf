package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Titles_awards(
                          awardId: Int,
                          titleId: Int
                        )

object Titles_awards {

  private[mine] lazy val raw = getDataset("titles_awards.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Titles_awards] = Try {
    raw match {
      case List(tawId, aw, title) =>
        Titles_awards(
          aw.toInt,
          title.toInt
        )
    }
  }
}

