package bookshelf.mine.schema

import bookshelf.mine._
import bookshelf.mine.schema.Titles.SLength.SLength
import bookshelf.mine.schema.Titles.Type.Type

import scala.util.Try

case class Titles(
                  id: Int,
                  title: String,
                  translator: Option[String],
                  synopsis: Option[String],
                  noteId: Option[Int],
                  seriesId: Option[Int],
                  seriesNum: Option[Int],
                  storyLength: Option[SLength],
                  `type`: Option[Type],
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

  object SLength extends Enumeration {
    type SLength = Value
    val nv = Value("NOVELLA")
    val ss = Value("SHORTSTORY")
    val jvn = Value("JUVENILE_FICTION")
    val nvz = Value("NOVELIZATION")
    val sf = Value("SHORT_FICTION")
  }

  private[mine] lazy val raw = getDataset("titles.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Titles] = Try {
    raw match {
      case List(id, title, translator, synopsis, noteId, serieId, serieNb, length, tType, parent, language, graphic) =>
        Titles(
          id.toInt,
          title,
          nullOrString(translator),
          nullOrString(synopsis),
          intOrNone(noteId),
          intOrNone(serieId),
          intOrNone(serieNb),
          parseLength(length),
          parseType(tType),
          intOrNone(parent),
          intOrNone(language),
          parseBool(graphic)
        )
    }
  }

  def parseLength(raw: String): Option[SLength] = {
    raw match {
      case "nv" => Some(SLength.nv)
      case "ss" => Some(SLength.ss)
      case "jvn" => Some(SLength.jvn)
      case "nvz" => Some(SLength.nvz)
      case "sf" => Some(SLength.sf)
      case _ => None
    }
  }

  def parseType(raw: String): Option[Type] = {
    raw match {
      case "ANTHOLOGY" => Some(Type.ANTHOLOGY)
      case "BACKCOVERART" => Some(Type.BACKCOVERART)
      case "COLLECTION" => Some(Type.COLLECTION)
      case "COVERART" => Some(Type.COVERART)
      case "INTERIORART" => Some(Type.INTERIORART)
      case "EDITOR" => Some(Type.EDITOR)
      case "ESSAY" => Some(Type.ESSAY)
      case "INTERVIEW" => Some(Type.INTERVIEW)
      case "NOVEL" => Some(Type.NOVEL)
      case _ => None
    }
  }

}
