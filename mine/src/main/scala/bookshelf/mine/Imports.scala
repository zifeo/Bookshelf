package bookshelf.mine

import io.getquill._
import io.getquill.naming.SnakeCase
import scala.concurrent.ExecutionContext.Implicits.global

private[mine] object Imports extends App {

  val db = source(new PostgresAsyncSourceConfig[SnakeCase]("db"))

  val ts = quote {
    query[TitleA].map(t => t.id)
  }

  println(db.run(ts))

}
