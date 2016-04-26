package bookshelf.saloon

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentType, HttpEntity, HttpResponse, MediaTypes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import spray.json._

import scala.concurrent.duration._
import scala.language.{implicitConversions, postfixOps}

private[saloon] object Main extends App {

  implicit val system = ActorSystem("Bookshelf-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  import DefaultJsonProtocol._
  import SprayJsonSupport._

  implicit val timeout = Timeout(15 seconds)

  implicit def toJson[T: JsonWriter](obj: T): JsValue =
    obj.toJson

  def format2SearchResults[A : JsonFormat, B : JsonFormat, C : JsonFormat, D : JsonFormat]
  (res: List[(A, B, C, D)]): JsValue =
    res.map {
      case (a, b, None, None) =>
        JsObject(
          "url" -> a,
          "title" -> b
        )
      case (a, b, c, None) =>
        JsObject(
          "url" -> a,
          "title" -> b,
          "description" -> c
        )
      case (a, b, None, d) =>
        JsObject(
          "url" -> a,
          "title" -> b,
          "image" -> d
        )
      case (a, b, c, d) =>
        JsObject(
          "url" -> a,
          "title" -> b,
          "description" -> c,
          "image" -> d
        )
    }.toJson

  val routes =
    get {
      path("search" / Segment) { term =>
        complete {
          for {
            auth <- Search.authors(term)
            pub <- Search.publications(term)
            titl <- Search.titles(term)
          } yield {
            val authJson = format2SearchResults(auth)
            val pubJson = format2SearchResults(pub)
            val titlJson = format2SearchResults(titl)
            val json =
              if (auth.isEmpty && pub.isEmpty && titl.isEmpty)
                JsObject("results" -> JsArray())
              else
                JsObject(
                  "results" -> JsObject(
                    "category1" -> JsObject(
                      "name" -> "Authors",
                      "results" -> format2SearchResults(auth)
                    ),
                    "category2" -> JsObject(
                      "name" -> "Publications",
                      "results" -> format2SearchResults(pub)
                    ),
                    "category3" -> JsObject(
                      "name" -> "Titles",
                      "results" -> format2SearchResults(titl)
                    )
                  )
                )
            HttpResponse(entity = HttpEntity(ContentType(MediaTypes.`application/json`), json.compactPrint))

          }
        }
      } ~
        path("authors" / IntNumber) { id =>
          complete {
            Queries.authors(id)
          }
        } ~
        path("publications" / IntNumber) { id =>
          complete {
            Queries.publications(id)
          }
        } ~
        path("titles" / IntNumber) { id =>
          complete {
            Queries.titles(id)
          }
        } ~
        path("presets" / IntNumber) { numQuery =>
          validate(Presets.exists(numQuery), "unknown query number") {
            complete {
              Presets(numQuery).map { case (headers, rows) =>
                JsObject(
                  "headers" -> headers,
                  "rows" -> rows
                )
              }
            }
          }
        } ~
        pathSingleSlash {
          getFromFile("../static/index.html")
        } ~
        getFromDirectory("../static")
    }

  val bind = Http().bindAndHandle(
    routes,
    config.getString("http.interface"),
    config.getInt("http.port")
  )

}
