package bookshelf.mine

import bookshelf._
import bookshelf.mine.schema._
import io.getquill._
import io.getquill.naming.SnakeCase

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.{implicitConversions, postfixOps}

private[mine] object Imports extends App {

  import CSVSources._

  implicit val db = source(new PostgresAsyncSourceConfig[SnakeCase]("db"))

  //truncate("authors")

  val res = List(
    //db.run(quote(query[Authors].insert))(authors.flatMap(_.toOption)),
    //db.run(quote(query[Awards].insert))(awards.flatMap(_.toOption)),
    //db.run(quote(query[AwardsCategories].insert))(awardsCategories.flatMap(_.toOption)),
    //db.run(quote(query[AwardsTypes].insert))(awardsTypes.flatMap(_.toOption)),
    //db.run(quote(query[Languages].insert))(languages.flatMap(_.toOption)),
    //db.run(quote(query[Notes].insert))(notes.flatMap(_.toOption))

    //db.run(quote(query[PublicationsAuthors].insert))(publicationsAuthors.flatMap(_.toOption)),
    //db.run(quote(query[PublicationsContents].insert))(publicationsContents.flatMap(_.toOption)),
    //db.run(quote(query[PublicationsSeries].insert))(publicationsSeries.flatMap(_.toOption)),
    //db.run(quote(query[Publications].insert))(publications.flatMap(_.toOption)),
    //db.run(quote(query[Publishers].insert))(publishers.flatMap(_.toOption)),
    db.run(quote(query[Reviews].insert))(reviews.flatMap(_.toOption))
  )

  Await.result(Future.sequence(res), Duration.Inf) foreach println

}
