package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class TitleTranslator(
                            titleId: Int,
                            translatorId: Int,
                            year: Int,
                            language: String
                          )

object TitleTranslators {

  private var counter = 1

  def inc(): Int = {
    counter += 1
    counter
  }


  def parseCols(raw: List[String]): Try[List[TitleTranslator]] = Try {
    raw match {
      case List(id, _, translator, _, _, _, _, _, _, _, _, _) =>
        translator.split(';').map(t => t.split(',').toList match {
          case List(language, year, _) => TitleTranslator(
            id.toInt,
            inc,
            year.toInt,
            language
          )
        }).toList
    }
  }

}

