package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Authors(
                    id: Int,
                    name: String,
                    legalName: String,
                    lastName: String,
                    pseudonym: String,
                    birthPlace: String,
                    birthDate: java.util.Date,
                    deathDate: java.util.Date,
                    email: String,
                    image: String,
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
          lastName,
          pseudonym,
          birthPlace,
          stringToDate(birthDate),
          stringToDate(deathDate),
          email,
          image,
          intOrNone(languageId),
          intOrNone(noteId))
    }
  }

}
