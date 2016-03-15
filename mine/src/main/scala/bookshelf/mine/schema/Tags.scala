package bookshelf.mine.schema

import scala.util.Try

case class Tags(
                 id: Int,
                 name: String
               )

object Tags {

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
