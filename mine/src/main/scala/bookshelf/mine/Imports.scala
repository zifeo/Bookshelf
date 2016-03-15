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

  private implicit val db = source(new PostgresAsyncSourceConfig[SnakeCase]("db"))

  truncate("authors")

  val res = Future.sequence(List(

    db.run(quote(query[Authors].insert))(authors.flatMap(_.toOption))


  ))

  Await.result(res, Duration.Inf) foreach println

}
