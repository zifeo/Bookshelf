package bookshelf.mine

import scala.util.Try

case class Tag(
                id: Int,
                name: String
              )

object Tag {

  val raw = getDataset("tags.csv")
  val all = raw.map(parseCols)

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
