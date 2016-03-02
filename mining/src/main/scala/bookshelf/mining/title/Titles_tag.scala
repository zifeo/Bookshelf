package bookshelf.mining.title

import bookshelf.mining._

import scala.util.Try

case class Titles_tag(
                       id: Int,
                       tag_id: Int,
                       title_id: Int
                     )

object Titles_tag {
  val raw = getDataset("titles_tag.csv")
  val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Titles_tag] = Try {
    raw match {
      case List(id, tag, title) =>
        Titles_tag(
          id.toInt,
          tag.toInt,
          title.toInt
        )
    }
  }
}
