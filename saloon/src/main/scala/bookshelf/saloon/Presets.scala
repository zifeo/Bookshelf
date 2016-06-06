package bookshelf.saloon

import com.github.mauricio.async.db.postgresql.PostgreSQLConnection
import com.github.mauricio.async.db.postgresql.util.URLParser

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.io.Source
import scala.language.postfixOps

object Presets {

  val db = {
    val host = config.getString("db.host")
    val port = config.getString("db.port")
    val user = config.getString("db.user")
    val password = config.getString("db.password")
    val database = config.getString("db.database")
    val url = URLParser.parse(s"jdbc:postgresql://$host:$port/$database?user=$user&password=$password")
    val connection = new PostgreSQLConnection(url)
    Await.result(connection.connect, 5 seconds)
  }

  val queries = Source.fromFile("../queries.sql").mkString.split(';').map(_.trim)

  def sql(query: String, args: List[String]): Future[(List[String], List[List[String]])] = {
    val q =
      if (query.contains("?") && args.nonEmpty)
        db.sendPreparedStatement(query, args)
      else
        db.sendQuery(query)


    q.map { resQuery =>
      resQuery.rows match {
        case Some(res) =>
          val cols = res.columnNames.toList
          val rows = res.toList.map { row =>
            row
              .filter(_ != null)
              .map(_.toString)
              .toList
          }
          (cols, rows)
        case None =>
          (List.empty, List.empty)
      }
    }
  }

  def exists(numQuery: Int): Boolean =
    numQuery - 1 < queries.length && numQuery > 0

  def apply(numQuery: Int, args: List[String]): Future[(List[String], List[List[String]])] =
    sql(queries(numQuery - 1), args)

}
