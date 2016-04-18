package bookshelf.saloon

import bookshelf.mine.schema._
import io.getquill._
import io.getquill.sources.sql.ops._

import scala.concurrent.Future

object Search {

  val queries = List(
    db.run(quote((term: String) => query[Authors].filter(_.name like s"%$term%"))),
    db.run(quote((term: String) => query[Publications].filter(_.title like s"%$term%"))),
    db.run(quote((term: String) => query[PublicationsSeries].filter(_.name like s"%$term%"))),
    db.run(quote((term: String) => query[Publishers].filter(_.name like s"%$term%"))),
    db.run(quote((term: String) => query[Awards].filter(_.title like s"%$term%"))),
    //db.run(quote((term: String) => query[AwardsCategories].filter(_.name like s"%$term%")),
    //db.run(quote((term: String) => query[AwardsTypes].filter(_.name like s"%$term%")),
    db.run(quote((term: String) => query[Notes].filter(_.note like s"%$term%"))),
    db.run(quote((term: String) => query[Tags].filter(_.name like s"%$term%"))),
    //db.run(quote((term: String) => query[Languages].filter(_.name like s"%$term%")),
    db.run(quote((term: String) => query[Titles].filter(_.title like s"%$term%"))),
    db.run(quote((term: String) => query[TitlesSeries].filter(_.title like s"%$term%")))
  )

  def apply(term: String): Future[Map[String, List[Any]]] = {
    Future.sequence(queries.map(_(term))).map { results =>
      results.map { res =>
        "t" -> res
      }.toMap
    }
  }

}
