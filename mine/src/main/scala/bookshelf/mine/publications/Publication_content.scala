package bookshelf.mine.publications

import bookshelf.mine._

import scala.util.Try

case class Publication_content(
                                pubc_id: Int,
                                title_id: Int,
                                publication_id: Int
                              )

object Publication_content {

  val raw = getDataset("publications_content.csv")
  val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Publication_content] = Try {
    raw match {
      case List(id, title, pub) =>
        Publication_content(
          id.toInt,
          title.toInt,
          pub.toInt
        )
    }
  }

}

