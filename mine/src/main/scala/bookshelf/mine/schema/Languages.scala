package bookshelf.mine.schema

import scala.util.Try
import bookshelf.mine._

case class Languages(
                      id: Int,
                      name: String,
                      code: String,
                      script: Option[Boolean]
                    )

object Languages {

  def parseCols(raw: List[String]): Try[Languages] = Try {
    raw match {
      case List(id, name, code, script) =>
        Languages(
          id.toInt,
          name,
          code,
          booleanOrNone(script)
        )
    }
  }

}
