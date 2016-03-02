package bookshelf.mine.titles

import bookshelf.mine._

import scala.util.Try

case class Reviews(
                    id: Int,
                    title_id: Int,
                    review_id: Int
                  )

object Reviews {
  val raw = getDataset("reviews.csv")
  val all = raw.map(parseCols)

  def parseCols(raw: List[String]): Try[Reviews] = Try {
    raw match {
      case List(id, title, review) =>
        Reviews(
          id.toInt,
          title.toInt,
          review.toInt
        )
    }
  }
}
