package bookshelf.mine.schema

import bookshelf.mine._
import bookshelf.mine.schema.Title.SLength.SLength
import bookshelf.mine.schema.Title.Type.Type

import scala.util.Try

case class Title(
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

object Title {

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

  def parseCols(raw: List[String]): Try[Title] = Try {
    raw match {
      case List(id, title, translator, synopsis, note_id, serie_id, serie_nb, length, t_type, parent, language, graphic) =>
        Title(
          id.toInt,
          title,
          parseString(translator),
          parseString(synopsis),
          intOrNone(note_id),
          intOrNone(serie_id),
          intOrNone(serie_nb),
          parseLength(length),
          parseType(t_type),
          intOrNone(parent),
          intOrNone(language),
          graphic)
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
