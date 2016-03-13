package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Languages(
                     id: Int,
                     name: String,
                     code: String,
                     script: String
                   )

object Languages {

  private[mine] lazy val raw = getDataset("languages.csv")
  private[mine] lazy val all = raw.map(parseCols)

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
