package bookshelf.saloon

import bookshelf.mine.schema._
import io.getquill._
import io.getquill.sources.sql.ops._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import bookshelf._

object Search {

  val authQuery = db.run(quote { term: String =>
    query[Authors]
      .filter(_.name like term)
      .map(a => (a.id, a.name, a.birthPlace, a.image))
      .take(4)
  })

  val pubQuery = db.run(quote { term: String =>
    query[Publications]
      .filter(_.title like term)
      .map(a => (a.id, a.title, a.`type`, a.cover))
      .take(4)
  })

  val titlQuery = db.run(quote { term: String =>
    query[Titles]
      .filter(_.title like term)
      .map(a => (a.id, a.title, a.storyLength))
      .take(4)
  })

  def authors(term: String): Future[List[(Int, String, Option[String], Option[String])]] =
    authQuery(s"%$term%")

  def publications(term: String): Future[List[(Int, String, Option[String], Option[String])]] =
    pubQuery(s"%$term%")

  def titles(term: String): Future[List[(Int, String, Option[String], Option[String])]] =
    titlQuery(s"%$term%").map(_.map { case (a, b, c) =>
      (a, b, c, None)
    })

}
