package bookshelf.mining.publication

import bookshelf.mining._

import scala.util.Try

case class Publication_serie(
                                ps_id: Int,
                                ps_name: String,
                                note_id: Option[Int]
                              )

object Publication_serie {

  val raw = getDataset("publications_series.csv")
  val all = raw.map(parseCols)

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
