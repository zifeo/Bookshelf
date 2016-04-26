package bookshelf.saloon

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentType, HttpEntity, HttpResponse, MediaTypes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import spray.json._

import scala.language.implicitConversions

private[saloon] object Main extends App {

  implicit val system = ActorSystem("Bookshelf-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  import DefaultJsonProtocol._
  import SprayJsonSupport._

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
    path("search" / Segment) { term =>
      get {
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
      pathSingleSlash {
        getFromFile("../static/index.html")
      } ~
      getFromDirectory("../static")

  val bind = Http().bindAndHandle(
    routes,
    config.getString("http.interface"),
    config.getInt("http.port")
  )

}
