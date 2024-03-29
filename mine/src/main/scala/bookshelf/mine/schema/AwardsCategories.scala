package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class AwardsCategories(
                             id: Int,
                             name: String,
                             typeId: Int,
                             ordr: Option[Int],
                             noteId: Option[Int]
                           )

object AwardsCategories {

  def parseCols(raw: List[String]): Try[AwardsCategories] = Try {
    raw match {
      case List(id, name, typeId, categoryOrder, noteId) =>
        AwardsCategories(
          id.toInt,
          name,
          typeId.toInt,
          intOrNone(categoryOrder),
          intOrNone(noteId)
        )
    }
  }

}
