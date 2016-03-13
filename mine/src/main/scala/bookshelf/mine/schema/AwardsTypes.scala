package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class AwardsTypes(
                       id: Int,
                       code: String,
                       name: String,
                       noteId: Option[Int],
                       awardedBy: String,
                       awardedFor: String,
                       shortName: String,
                       poll: Boolean,
                       nonGenre: Boolean
                     )

object AwardsTypes {

  private[mine] lazy val raw = getDataset("award_types.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[AwardsTypes] = Try {
    raw match {
      case List(id, code, name, noteId, awardedBy, awardedFor, shortName, poll, nonGenre) =>
        AwardsTypes(
          id.toInt,
          code,
          name,
          intOrNone(noteId),
          awardedBy,
          awardedFor,
          shortName,
          parseBoolean(poll),
          parseBoolean(nonGenre)
        )
    }
  }

}
