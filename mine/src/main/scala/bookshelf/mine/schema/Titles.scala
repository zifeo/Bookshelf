package bookshelf.mine.schema

import bookshelf.mine._
import io.getquill._

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
                   parent: Option[Int],
                   languageId: Option[Int],
                   graphic: Boolean
                 )

object Titles {

  object Type extends Enumeration {
    type Type = Value
    val ANTHOLOGY = Value("ANTHOLOGY")
    val BACKCOVERART = Value("BACKCOVERART")
    val COLLECTION = Value("COLLECTION")
    val COVERART = Value("COVERART")
    val INTERIORART = Value("INTERIORART")
    val EDITOR = Value("EDITOR")
    val ESSAY = Value("ESSAY")
    val INTERVIEW = Value("INTERVIEW")
    val NOVEL = Value("NOVEL")
    val NONFICTION = Value("NONFICTION")
    val OMNIBUS = Value("OMNIBUS")
    val POEM = Value("POEM")
    val REVIEW = Value("REVIEW")
    val SERIAL = Value("SERIAL")
    val SHORTFICTION = Value("SHORTFICTION")
    val CHAPBOOK = Value("CHAPBOOK")
  }

  object Length extends Enumeration {
    type Length = Value
    val nv = Value("NOVELLA")
    val ss = Value("SHORTSTORY")
    val jvn = Value("JUVENILE_FICTION")
    val nvz = Value("NOVELIZATION")
    val sf = Value("SHORT_FICTION")
  }

  def parseCols(raw: List[String]): Try[Titles] = Try {
    raw match {
      case List(id, title, translator, synopsis, noteId, serieId, serieNb, length, tType, parent, language, graphic) =>
        Titles(
          id.toInt,
          title,
          stringOrNone(translator),
          stringOrNone(synopsis),
          intOrNone(noteId),
          intOrNone(serieId),
          intOrNone(serieNb),
          parseLength(length),
          parseType(tType),
          intOrNone(parent),
          intOrNone(language),
          parseBoolean(graphic)
        )
    }
  }

  def parseLength(raw: String): Option[String] = {
    raw match {
      case "nv" => Some(Length.nv.toString)
      case "ss" => Some(Length.ss.toString)
      case "jvn" => Some(Length.jvn.toString)
      case "nvz" => Some(Length.nvz.toString)
      case "sf" => Some(Length.sf.toString)
      case _ => None
    }
  }

  def parseType(raw: String): Option[String] = {
    raw match {
      case "ANTHOLOGY" => Some(Type.ANTHOLOGY.toString)
      case "BACKCOVERART" => Some(Type.BACKCOVERART.toString)
      case "COLLECTION" => Some(Type.COLLECTION.toString)
      case "COVERART" => Some(Type.COVERART.toString)
      case "INTERIORART" => Some(Type.INTERIORART.toString)
      case "EDITOR" => Some(Type.EDITOR.toString)
      case "ESSAY" => Some(Type.ESSAY.toString)
      case "INTERVIEW" => Some(Type.INTERVIEW.toString)
      case "NOVEL" => Some(Type.NOVEL.toString)
      case _ => None
    }
  }

}
