package bookshelf.saloon

import com.github.mauricio.async.db.{RowData, QueryResult}
import com.github.mauricio.async.db.postgresql.PostgreSQLConnection
import com.github.mauricio.async.db.postgresql.util.URLParser

import scala.concurrent.duration._
import scala.concurrent.{Future, Await}
import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global

object Presets extends App {

  private lazy val db = {
    val host = config.getString("db.host")
    val port = config.getString("db.port")
    val user = config.getString("db.user")
    val password = config.getString("db.password")
    val database = config.getString("db.database")
    val url = URLParser.parse(s"jdbc:postgresql://$host:$port/$database?user=$user&password=$password")
    val connection = new PostgreSQLConnection(url)
    Await.result(connection.connect, 5 seconds)
  }

  val sql1 =
    """
      |SELECT A.name, COUNT(*) AS count
      |FROM PUBLICATIONS_AUTHORS P
      |INNER JOIN AUTHORS A ON P.author_id = A.id
      |GROUP BY A.name
      |ORDER BY count DESC
      |LIMIT 10;
      |
    """.stripMargin

  def sql(query: String): Future[List[Map[String, String]]] =
    db.sendQuery(query).map { resQuery =>
      resQuery.rows match {
        case Some(res) =>
          val cols = res.columnNames
          res.toList.map {row =>
            cols.zip(row.map(_.toString)).toMap
          }
        case None =>
          throw new Exception("no result")
      }
    }


  val result = Await.result(sql(sql1), 5 seconds )

  println(result)

}
