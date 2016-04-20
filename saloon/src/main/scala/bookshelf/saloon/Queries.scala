package bookshelf.saloon

import bookshelf.mine.schema.Authors
import io.getquill._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global._
import bookshelf._
import scala.concurrent.ExecutionContext.Implicits.global

object Queries {

  trait Result
  case class Query1Result()

  val mapping = Map(
    0 -> quote(infix"Select * from Publications".as[Query[Query1Result]])
  )

  /*def apply(queryNum: Int): Future[Result] = {
    val q = mapping(queryNum)
    db.run(q)
  }*/

  val authorsId = quote { id: Int =>
    query[Authors]
      .filter(_.id == id)
  }

  def authors(id: Int): Future[Option[Authors]] =
    db.run(authorsId)(id).map(_.headOption)

}
