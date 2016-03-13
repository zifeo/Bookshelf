package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Tags(
                id: Int,
                name: String
              )

object Tags {

  private[mine] lazy val raw = getDataset("tags.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Tags] = Try {
    raw match {
      case List(id, name) =>
        Tags(
          id.toInt,
          name
        )
    }
  }

}
