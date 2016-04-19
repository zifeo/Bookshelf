package bookshelf.mine

import java.sql.BatchUpdateException

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
import scala.util.Try

private[mine] object Imports extends App {

  import CSVSources._

  val db = source(new JdbcSourceConfig[PostgresDialect, SnakeCase]("db"))

  try {
    db.run(quote(query[Publications].insert))(Publications(10000, "", DateTime.now, 0, None, None, None, None,
      None, None, None, None, None, None, None))
  } catch {
    case failedBatch: BatchUpdateException => failedBatch.getNextException.printStackTrace()
  }

  /*val res = List(
    batchInserts(db.run(quote(query[Authors].insert)), authors),
    batchInserts(db.run(quote(query[Awards].insert)), awards),
    batchInserts(db.run(quote(query[AwardsCategories].insert)), awardsCategories),
    batchInserts(db.run(quote(query[AwardsTypes].insert)), awardsTypes),
    batchInserts(db.run(quote(query[Languages].insert)), languages),
    batchInserts(db.run(quote(query[Notes].insert)), notes),

    batchInserts(db.run(quote(query[PublicationsAuthors].insert)), publicationsAuthors),
    batchInserts(db.run(quote(query[PublicationsContents].insert)), publicationsContents),
    batchInserts(db.run(quote(query[PublicationsSeries].insert)), publicationsSeries),
    batchInserts(db.run(quote(query[Publications].insert)), publications),
    batchInserts(db.run(quote(query[Publishers].insert)), publishers),
    batchInserts(db.run(quote(query[Reviews].insert)), reviews),

    batchInserts(db.run(quote(query[Tags].insert)), tags),
    batchInserts(db.run(quote(query[TitlesSeries].insert)), titlesSeries),
    batchInserts(db.run(quote(query[Titles].insert)), titles),
    batchInserts(db.run(quote(query[TitlesAwards].insert)), titlesAwards),
    batchInserts(db.run(quote(query[TitlesTags].insert)), titlesTags),
    batchInserts(db.run(quote(query[Webpages].insert)), webpages)
  )

  def batchInserts[T](query: List[T] => List[Long], data: List[Try[T]]): Future[List[Long]] = {
    val queries = Future {
      data.flatMap(_.toOption).distinct.grouped(2000).flatMap(group => query(group)).toList
    }
    queries onFailure {
      case failedBatch: BatchUpdateException => failedBatch.getNextException.printStackTrace()
      case failedMatch: MatchError => failedMatch.printStackTrace()
      case other => other.printStackTrace()
    }
    queries
  }

  Await.result(Future.sequence(res), Duration.Inf)*/

}
