package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class PublicationsSeries(
                                id: Int,
                                name: String,
                                noteId: Option[Int]
                              )

object PublicationsSeries {

  private[mine] lazy val raw = getDataset("publications_series.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[PublicationsSeries] = Try {
    raw match {
      case List(id, title, pub) =>
        PublicationsSeries(
          id.toInt,
          title,
          intOrNone(pub)
        )
    }
  }

}

