package bookshelf.mine.schema

import bookshelf.mine._

import scala.collection.mutable
import scala.util.Try

case class Titles(
                   id: Int,
                   title: String,
                   translator: Option[String],
                   synopsis: Option[String],
                   noteId: Option[Int],
                   seriesId: Option[Int],
                   seriesNum: Option[Int],
                   storyLength: Option[String],
                   `type`: Option[String],
                   parent: Int,
                   languageId: Option[Int],
                   graphic: Option[Boolean]
                 )

object Titles {

  val types = List("ANTHOLOGY", "BACKCOVERART", "COLLECTION", "COVERART", "INTERIORART", "EDITOR", "ESSAY", "INTERVIEW",
    "NOVEL", "NONFICTION", "OMNIBUS", "POEM", "REVIEW", "SERIAL", "SHORTFICTION", "CHAPBOOK").map(_.toLowerCase)

  val lengths = List("NOVELLA", "SHORTSTORY", "JUVENILE_FICTION", "NOVELIZATION", "SHORT_FICTION").map(_.toLowerCase)

  val v = mutable.Set.empty[String]

  def parseCols(raw: List[String]): Try[Titles] = Try {
    raw match {
      case List(id, title, translator, synopsis, noteId, serieId, serieNb, length, tpe, parent, language, graphic) =>
        v += translator
        Titles(
          id.toInt,
          title,
          stringOrNone(translator),
          stringOrNone(synopsis),
          intOrNone(noteId),
          intOrNone(serieId),
          intOrNone(serieNb),
          requireIn(length, lengths),
          requireIn(tpe, types),
          intOrZero(parent),
          intOrNone(language),
          booleanOrNone(graphic)
        )
    }
  }

}
