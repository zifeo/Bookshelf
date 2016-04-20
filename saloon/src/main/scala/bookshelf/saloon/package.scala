package bookshelf

import java.time.Instant
import java.util.Date

import bookshelf.mine.schema.Authors
import io.getquill._
import io.getquill.naming.SnakeCase
import spray.json._

package object saloon {

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


}
