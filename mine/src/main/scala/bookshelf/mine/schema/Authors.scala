package bookshelf.mine.schema

import java.util.Date

import bookshelf.mine._
import org.joda.time.DateTime

import scala.util.Try

case class Authors(
                    id: Int,
                    name: String,
                    legalName: Option[String],
                    lastName: Option[String],
                    pseudonym: Option[Int],
                    birthPlace: Option[String],
                    birthDate: Option[Date],
                    deathDate: Option[Date],
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
          stringOrNone(legalName),
          stringOrNone(lastName),
          intOrNone(pseudonym),
          stringOrNone(birthPlace),
          stringToDate(birthDate),
          stringToDate(deathDate),
          stringOrNone(email),
          stringOrNone(image),
          intOrNone(languageId),
          intOrNone(noteId)
        )
    }
  }

}
