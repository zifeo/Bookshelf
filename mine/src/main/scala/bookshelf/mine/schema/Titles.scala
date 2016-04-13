package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Titles(
                   id: Int,
                   title: String,
                   translatorId: Option[Int],
                   synopsis: Option[String],
                   noteId: Option[Int],
                   seriesId: Option[Int],
                   seriesNum: Option[Int],
                   storyLength: Option[String],
                   `type`: Option[String],
                   parent: Int,
                   languageId: Option[Int],
                   graphic: Boolean
                 )

object Titles {

  val types = List("ANTHOLOGY", "BACKCOVERART", "COLLECTION", "COVERART", "INTERIORART", "EDITOR", "ESSAY", "INTERVIEW",
    "NOVEL", "NONFICTION", "OMNIBUS", "POEM", "REVIEW", "SERIAL", "SHORTFICTION", "CHAPBOOK").map(_.toLowerCase)

  val lengths = List("NOVELLA", "SHORTSTORY", "JUVENILE_FICTION", "NOVELIZATION", "SHORT_FICTION").map(_.toLowerCase)

  private var counter = 1

  def parseTranslator(raw: String): Option[Int] = {
    if (stringOrNone(raw).isDefined) {
      counter += 1
      Some(counter)
    } else None
  }

  def parseCols(raw: List[String]): Try[Titles] = Try {

    raw match {
      case List(id, title, translator, synopsis, noteId, serieId, serieNb, length, tpe, parent, language, graphic) =>
        Titles(
          id.toInt,
          title,
          parseTranslator(translator),
          stringOrNone(synopsis),
          intOrNone(noteId),
          intOrNone(serieId),
          intOrNone(serieNb),
          requireIn(length, lengths),
          requireIn(tpe, types),
          intOrZero(parent),
          intOrNone(language),
          parseBoolean(graphic)
        )
    }
  }

}
