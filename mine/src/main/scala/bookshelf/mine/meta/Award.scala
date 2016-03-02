package bookshelf.mine

import scala.util.Try

case class Award(
                  id: Int,
                  title: String,
                  date: java.util.Date,
                  type_code: String,
                  type_id: Int,
                  category_id: Int,
                  note_id: Option[Int]
                )

object Award {

  private[mine] lazy val raw = getDataset("awards.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Award] = Try {
    raw match {
      case List(id, title, date, type_code, type_id, category_id, note_id) =>
        Award(
          id.toInt,
          title,
          stringToDate(date),
          type_code,
          type_id.toInt,
          category_id.toInt,
          intOrNone(note_id)
        )
    }
  }

}
