package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Translators(
                       id: Int,
                       name: String
                     )

object Translators {

  private var visited = List.empty[String]

  def inc(name: String): Int = {
    if (!visited.contains(name)) {
      visited = visited :+ name
    }
    visited.indexOf(name) + 1
  }

  def parseCols(raw: List[String]): Try[List[Translators]] = Try {
    raw match {
      case List(id, _, translator, _, _, _, _, _, _, _, _, _) =>
        translator.split(';').toList.map { t =>
          t.split(',').toList match {
            case List(_, _, name) => Translators(
              inc(name),
              name
            )
          }
        }
    }
  }

}

