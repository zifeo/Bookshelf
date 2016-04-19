package bookshelf.mine.schema

import bookshelf.mine._

import scala.collection.mutable
import scala.util.Try

case class Titles(
                   id: Int,
                   title: String,
                   synopsis: Option[Int],
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

  val v = mutable.Set.empty[String]

  def parseCols(raw: List[String]): Try[Titles] = Try {

    raw match {
      case List(id, title, translator, synopsis, noteId, serieId, serieNb, length, tpe, parent, language, graphic) =>
        v += translator
        Titles(
          id.toInt,
          title,
          intOrNone(synopsis),
          intOrNone(noteId),
          intOrNone(serieId),
          intOrNone(serieNb),
          requireIn(length, TitlesLengths.values),
          requireIn(tpe, TitlesTypes.values),
          intOrZero(parent),
          intOrNone(language),
          booleanOrNone(graphic)
        )
    }
  }

}
