package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Title(
                  title_id: Int,
                  title: String,
                  translator: Option[String],
                  synopsis: Option[String],
                  note_id: Option[Int],
                  serie_id: Option[Int],
                  serie_number: Option[Int],
                  length: Option[SLength],
                  title_type: Option[Type],
                  parent_id: Option[Int],
                  language_id: Option[Int],
                  title_graphic: String
                )

object Title {

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

  def parseString(raw: String): Option[String] = {
    raw match {
      case "\\N" => None
      case x => Some(x)
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
