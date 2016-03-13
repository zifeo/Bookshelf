package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class AwardsCategories(
                           id: Int,
                           name: String,
                           typeId: Int,
                           order: String,
                           noteId: Option[Int]
                         )

object AwardsCategories {

  private[mine] lazy val raw = getDataset("award_categories.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[AwardsCategories] = Try {
    raw match {
      case List(id, name, type_id, category_order, note_id) =>
        AwardsCategories(
          id.toInt,
          name,
          type_id.toInt,
          category_order,
          intOrNone(note_id)
        )
    }
  }

}
