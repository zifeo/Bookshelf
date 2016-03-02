package bookshelf.mine.meta

import bookshelf.mine._

import scala.util.Try

case class Award_type(
                       id: Int,
                       type_code: String,
                       type_name: String,
                       note_id: Option[Int],
                       awarded_by: String,
                       awarded_for: String,
                       short_name: String,
                       poll: Boolean,
                       non_genre: Boolean
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
