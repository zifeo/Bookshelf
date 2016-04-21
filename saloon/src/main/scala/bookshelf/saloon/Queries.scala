package bookshelf.saloon

import bookshelf.mine.schema.{Titles, Publications, Authors}
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

  private object Query {

    val authById = quote { id: Int =>
      query[Authors]
        .filter(_.id == id)
    }

    val pubById = quote { id: Int =>
      query[Publications]
        .filter(_.id == id)
    }

    val titlById = quote { id: Int =>
      query[Titles]
        .filter(_.id == id)
    }
  }

  def authors(id: Int): Future[Authors] =
    db.run(Query.authById)(id).map(_.head)

  def publications(id: Int): Future[Publications] =
    db.run(Query.pubById)(id).map(_.head)

  def titles(id: Int): Future[Titles] =
    db.run(Query.titlById)(id).map(_.head)

}
