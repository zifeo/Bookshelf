package bookshelf.mine.schema

import bookshelf.mine._
import org.joda.time.DateTime

import scala.util.Try

case class Authors(
                    id: Int,
                    name: String,
                    legalName: String,
                    lastName: Option[String],
                    pseudonym: Option[Int],
                    birthPlace: Option[String],
                    birthDate: Option[DateTime],
                    deathDate: Option[DateTime],
                    email: Option[String],
                    image: Option[String],
                    languageId: Option[Int],
                    noteId: Option[Int]
                  )

object Authors {

  def parseCols(raw: List[String]): Try[Authors] = Try {
    raw match {
      case List(id, name, legalName, lastName, pseudonym, birthPlace, birthDate, deathDate, email, image, languageId, noteId) =>
        Authors(
          id.toInt,
          name,
          legalName,
          stringOrNone(lastName),
          intOrNone(pseudonym),
          stringOrNone(birthPlace),
          stringOrNone(birthDate).map(stringToDate),
          stringOrNone(deathDate).map(stringToDate),
          stringOrNone(email),
          stringOrNone(image),
          intOrNone(languageId),
          intOrNone(noteId))
    }
  }

}
