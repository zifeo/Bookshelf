package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Publishers(
                       id: Int,
                       name: String,
                       noteId: Option[Int]
                     )

object Publishers {

  def parseCols(raw: List[String]): Try[Publishers] = Try {
    raw match {
      case List(id, name, note) =>
        Publishers(
          id.toInt,
          name,
          intOrNone(note)
        )
    }
  }

}

