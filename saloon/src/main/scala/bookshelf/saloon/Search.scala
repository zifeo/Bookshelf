package bookshelf.saloon

import bookshelf.mine.schema._
import io.getquill._
import io.getquill.sources.sql.ops._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Search {

  private object Query {

    val auth = db.run(quote { term: String =>
      query[Authors]
        .filter(_.name.toLowerCase like term)
        .map(a => (a.id, a.name, a.birthPlace, a.image))
        .take(4)
    })

    val pub = db.run(quote { term: String =>
      query[Publications]
        .filter(_.title.toLowerCase like term)
        .map(a => (a.id, a.title, a.`type`, a.cover))
        .take(4)
    })

    val titl = db.run(quote { term: String =>
      query[Titles]
        .filter(_.title.toLowerCase like term)
        .map(a => (a.id, a.title, a.storyLength))
        .take(4)
    })
  }

  type SearchResult = Future[List[(String, String, Option[String], Option[String])]]

  def authors(term: String): SearchResult =
    Query.auth(s"%$term%").map(_.map { case (a, b, c, d) =>
      (s"authors/$a", b, c, d)
    })

  def publications(term: String): SearchResult =
    Query.pub(s"%$term%").map(_.map { case (a, b, c, d) =>
      (s"publications/$a", b, c, d)
    })

  def titles(term: String): SearchResult =
    Query.titl(s"%$term%").map(_.map { case (a, b, c) =>
      (s"titles/$a", b, c, None)
    })

}
