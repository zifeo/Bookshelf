package bookshelf.mine.schema

import scala.util.Try

case class Reviews(
                    titleId: Int,
                    reviewId: Int
                  )

object Reviews {

  def parseCols(raw: List[String]): Try[Reviews] = Try {
    raw match {
      case List(id, title, review) =>
        Reviews(
          title.toInt,
          review.toInt
        )
    }
  }
}
