package bookshelf.mining

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

  val raw = getDataset("authors.csv")
  val all = raw.map(parseCols)

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
