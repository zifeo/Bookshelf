package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Translator(
                       id: Int,
                       name: String
                     )

object Translators {

  private var visited: List[String] = List()

  def inc(name: String): Int = {
    if (!visited.contains(name)) {
      visited = visited :+ name
    }
    visited.indexOf(name) + 1
  }

  def parseCols(raw: List[String]): Try[List[Translator]] = Try {
    raw match {
      case List(id, _, translator, _, _, _, _, _, _, _, _, _) =>
        translator.split(';').toList.map { t =>
          t.split(',').toList match {
            case List(_, _, name) => Translator(
              inc(name),
              name
            )
          }
        }
    }
  }

}

