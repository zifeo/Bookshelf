package bookshelf.saloon

import com.github.mauricio.async.db.{RowData, QueryResult}
import com.github.mauricio.async.db.postgresql.PostgreSQLConnection
import com.github.mauricio.async.db.postgresql.util.URLParser

import scala.concurrent.duration._
import scala.concurrent.{Future, Await}
import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global

object Presets extends App {

  val host = config.getString("db.host")
  val port = config.getString("db.port")
  val user = config.getString("db.user")
  val password = config.getString("db.password")
  val database = config.getString("db.database")

  val addr = URLParser.parse(s"jdbc:postgresql://$host:$port/$database?user=$user&password=$password")
  val connection = new PostgreSQLConnection(addr)

  Await.result(connection.connect, 5 seconds)

  val future: Future[QueryResult] = connection.sendQuery("SELECT 0")

  val mapResult: Future[Any] = future.map(queryResult => queryResult.rows match {
    case Some(resultSet) => {
      val row : RowData = resultSet.head
      row(0)
    }
    case None => -1
  }
  )

  val result = Await.result( mapResult, 5 seconds )

  println(result)

  connection.disconnect

}
