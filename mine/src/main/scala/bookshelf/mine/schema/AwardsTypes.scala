package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class AwardsTypes(
                        id: Int,
                        code: Option[String],
                        name: String,
                        noteId: Option[Int],
                        awardedBy: String,
                        awardedFor: String,
                        shortName: String,
                        poll: Boolean,
                        nonGenre: Boolean
                      )

object AwardsTypes {

  def parseCols(raw: List[String]): Try[AwardsTypes] = Try {
    raw match {
      case List(id, code, name, noteId, awardedBy, awardedFor, shortName, poll, nonGenre) =>
        AwardsTypes(
          id.toInt,
          stringOrNone(code),
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
