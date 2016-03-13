package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Authors(
                    id: Int,
                    name: String,
                    legalName: String,
                    lastName: Option[String],
                    pseudonym: String,
                    birthPlace: Option[String],
                    birthDate: Option[java.util.Date],
                    deathDate: Option[java.util.Date],
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
          pseudonym,
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
