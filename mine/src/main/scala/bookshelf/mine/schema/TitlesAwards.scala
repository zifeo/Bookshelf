package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class TitlesAwards(
                          awardId: Int,
                          titleId: Int
                        )

object TitlesAwards {

  private[mine] lazy val raw = getDataset("titles_awards.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[TitlesAwards] = Try {
    raw match {
      case List(tawId, aw, title) =>
        TitlesAwards(
          aw.toInt,
          title.toInt
        )
    }
  }
}

