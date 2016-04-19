package bookshelf.saloon

import bookshelf.mine.schema.Authors
import io.getquill._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global._
import bookshelf._

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

}
