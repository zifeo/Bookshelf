package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Award_type(
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

object Award_type {

  private[mine] lazy val raw = getDataset("award_types.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def getBoolean(raw: String): Boolean = raw.toLowerCase.equals("yes")

  def parseCols(raw: List[String]): Try[Award_type] = Try {
    raw match {
      case List(id, code, name, note_id, awarded_by, awarded_for, short_name, poll, non_genre) =>
        Award_type(
          id.toInt,
          code,
          name,
          intOrNone(note_id),
          awarded_by,
          awarded_for,
          short_name,
          getBoolean(poll),
          getBoolean(non_genre)
        )
    }
  }

}
