package bookshelf.mine.schema

import scala.util.Try

case class Languages(
                      id: Int,
                      name: String,
                      code: String,
                      script: String
                    )

object Languages {

  def parseCols(raw: List[String]): Try[Languages] = Try {
    raw match {
      case List(id, name, code, script) =>
        Languages(
          id.toInt,
          name,
          code,
          script
        )
    }
  }

}
