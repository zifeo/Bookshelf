package bookshelf.mine

import bookshelf._
import bookshelf.mine.schema._
import io.getquill._
import io.getquill.naming.SnakeCase
import io.getquill.sources.sql.idiom.PostgresDialect
import org.joda.time.DateTime

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.{implicitConversions, postfixOps}

private[mine] object Imports extends App {

  import CSVSources._

  implicit val db = source(new JdbcSourceConfig[PostgresDialect, SnakeCase]("db"))

  truncate("awards")

  val res = List(
    //db.run(quote(query[Authors].insert))(authors.flatMap(_.toOption)),
    //db.run(quote(query[Awards].insert))(awards.flatMap(_.toOption)),
    //db.run(quote(query[AwardsCategories].insert))(awardsCategories.flatMap(_.toOption)),
    //db.run(quote(query[AwardsTypes].insert))(awardsTypes.flatMap(_.toOption)),
    //db.run(quote(query[Languages].insert))(languages.flatMap(_.toOption)),
    //db.run(quote(query[Notes].insert))(notes.flatMap(_.toOption)),

    //db.run(quote(query[PublicationsAuthors].insert))(publicationsAuthors.flatMap(_.toOption)),
    //db.run(quote(query[PublicationsContents].insert))(publicationsContents.flatMap(_.toOption)),
    //db.run(quote(query[PublicationsSeries].insert))(publicationsSeries.flatMap(_.toOption)),
    //batchInserts(db.run(quote(query[Publications].insert)), publications.flatMap(_.toOption).take(1000))
    //db.run(quote(query[Publishers].insert))(publishers.flatMap(_.toOption)),
    //db.run(quote(query[Reviews].insert))(reviews.flatMap(_.toOption)),

    //db.run(quote(query[Tags].insert))(tags.flatMap(_.toOption)),
    //db.run(quote(query[TitlesSeries].insert))(titlesSeries.flatMap(_.toOption)),
    //db.run(quote(query[Titles].insert))(titles.flatMap(_.toOption)),
    //db.run(quote(query[TitlesAwards].insert))(titlesAwards.flatMap(_.toOption)),
    //db.run(quote(query[TitlesTags].insert))(titlesTags.flatMap(_.toOption)),,
    //db.run(quote(query[Webpages].insert))(webpages.flatMap(_.toOption))

  ).flatten

  def batchInserts[T](query: List[T] => List[Long], data: List[T]): List[Long] = {
    println(data.grouped(200).size)
    data.grouped(200).flatMap(group => query(group)).toList
  }

  val data = List(Awards(1,"Titan", DateTime.parse("1990-01-01T00:00:00.000+01:00"),1,7,None),
    Awards(2,"Class Six Climb",DateTime.parse("1979-01-01T00:00:00.000+01:00"),1,7,None),
    Awards(3,"The Visitors",DateTime.parse("1979-01-01T00:00:00.000+01:00"),1,7,None))

  println(data)
  val f = db.run(quote(query[Awards].insert))(data: List[Awards])




  //Await.result(f, Duration.Inf)

}
