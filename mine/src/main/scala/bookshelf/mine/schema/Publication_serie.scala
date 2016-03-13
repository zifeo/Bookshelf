package bookshelf.mine.schema

import bookshelf.mine._

import scala.util.Try

case class Publication_serie(
                                ps_id: Int,
                                ps_name: String,
                                note_id: Option[Int]
                              )

object Publication_serie {

  private[mine] lazy val raw = getDataset("publications_series.csv")
  private[mine] lazy val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Publication_serie] = Try {
    raw match {
      case List(id, title, pub) =>
        Publication_serie(
          id.toInt,
          title,
          intOrNone(pub)
        )
    }
  }

}

