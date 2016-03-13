package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Author(
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

object Author {

  private[mine] lazy val raw = getDataset("authors.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Author] = Try {
    raw match {
      case List(id, name, legalName, lastName, pseudonym, birthPlace, birthDate, deathDate, email, image, languageId, noteId) =>
        Author(
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
