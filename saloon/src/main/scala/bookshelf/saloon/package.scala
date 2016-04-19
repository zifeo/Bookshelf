package bookshelf

import io.getquill._
import io.getquill.naming.SnakeCase

package object saloon {

  val db = source(new PostgresAsyncSourceConfig[SnakeCase]("db"))

}
