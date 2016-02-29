package bookshelf.mining

import scala.util.Try

object Type extends Enumeration {
  type Type = Value
  val ANTHOLOGY, BACKCOVERART, COLLECTION, COVERART, INTERIORART, EDITOR, ESSAY, INTERVIEW, NOVEL = Value
}

object SLength extends Enumeration {
  type SLength = Value
  val nv = Value("novella")
  val ss = Value("short story")
  val jvn = Value("juvenile fiction")
  val nvz = Value("novellization")
  val sf = Value("short fiction")
}

case class Title(
                  id: Int,
                  title: String,
                  legalName: String,
                  lastName: String,
                  pseudonym: String,
                  birthPlace: String,
                  birthDate: String,
                  deathDate: String,
                  email: String,
                  image: String,
                  languageId: Option[Int],
                  noteId: Option[Int]
                )

object Title {

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
          birthDate,
          deathDate,
          email,
          image,
          intOrNone(languageId),
          intOrNone(noteId))
    }
  }

}
