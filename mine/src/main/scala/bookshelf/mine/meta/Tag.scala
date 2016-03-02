package bookshelf.mine.meta

import bookshelf.mine._

import scala.util.Try

case class Tag(
                id: Int,
                name: String
              )

object Tag {

  private[mine] lazy val raw = getDataset("tags.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Tag] = Try {
    raw match {
      case List(id, name) =>
        Tag(
          id.toInt,
          name
        )
    }
  }

}
