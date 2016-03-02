package bookshelf.mine

import scala.util.Try

case class Language(
                     id: Int,
                     name: String,
                     code: String,
                     script: String
                   )

object Language {

  val raw = getDataset("languages.csv")
  val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Language] = Try {
    raw match {
      case List(id, name, code, script) =>
        Language(
          id.toInt,
          name,
          code,
          script
        )
    }
  }

}
