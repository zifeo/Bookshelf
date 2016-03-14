package bookshelf.mine

import bookshelf.mine.schema._
import io.getquill._
import io.getquill.naming.SnakeCase

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

private[mine] object Imports extends App {

  val db = source(new PostgresAsyncSourceConfig[SnakeCase]("db"))

  val authors = quote(query[Authors].insert)
  val insert = db.run(authors)(CSVSources.authors.flatMap(_.toOption))

  println(Await.result(insert, 500 seconds))



}
