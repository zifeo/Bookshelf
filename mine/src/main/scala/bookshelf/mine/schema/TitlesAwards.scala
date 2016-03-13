package bookshelf.mine.schema

import scala.util.Try

case class TitlesAwards(
                         awardId: Int,
                         titleId: Int
                       )

object TitlesAwards {

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

