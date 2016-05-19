package bookshelf

import java.time.Instant
import java.util.Date
import java.util.logging.LogManager

import bookshelf.mine.schema.{Titles, Publications, Authors}
import bookshelf.saloon.Queries.NewTitle
import com.typesafe.config.ConfigFactory
import io.getquill._
import io.getquill.naming.SnakeCase
import spray.json._

package object saloon {

  LogManager.getLogManager.readConfiguration()
  val config = ConfigFactory.load()

  val db = source(new PostgresAsyncSourceConfig[SnakeCase]("db"))

  import DefaultJsonProtocol._

  implicit object DateTimeFormat extends RootJsonFormat[Date] {
    def write(obj: Date): JsValue = {
      obj.toInstant.toEpochMilli.toJson
    }

    def read(json: JsValue): Date = json match {
      case JsNumber(s) => Date.from(Instant.ofEpochMilli(s.toLong))
      case x => serializationError(s"unknown type: $x")
    }
  }

  implicit val authorsJF = jsonFormat12(Authors.apply)
  implicit val publicationsJF = jsonFormat15(Publications.apply)
  implicit val titlesJF = jsonFormat11(Titles.apply)
  implicit val newTitleJF = jsonFormat10(NewTitle)

}
