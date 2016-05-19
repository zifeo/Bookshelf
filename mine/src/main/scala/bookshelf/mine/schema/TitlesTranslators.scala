package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class TitlesTranslators(
                              titleId: Int,
                              translatorId: Int,
                              year: Int,
                              languageId: Int
                           )

object TitlesTranslators {

  private var visited = List.empty[String]

  def inc(name: String): Int = {
    if (!visited.contains(name)) {
      visited = visited :+ name
    }
    visited.indexOf(name) + 1
  }

  def getLanguageId(raw:String) : Int = raw.toLowerCase match {
    case "english" => 17
    case "italian" => 36
    case "romanian" => 54
    case "russian" => 55
    case _ => 0
  }

  def parseCols(raw: List[String]): Try[List[TitlesTranslators]] = Try {
    raw match {
      case List(id, _, translator, _, _, _, _, _, _, _, _, _) =>
        translator.split(';').map { t => t.split(',').toList match {
          case List(languageId, year, name) => TitlesTranslators(
            id.toInt,
            inc(name),
            year.toInt,
            getLanguageId(languageId)
          )
        }
        }.toList
    }
  }

}

