package bookshelf.saloon

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentType, HttpEntity, HttpResponse, MediaTypes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import spray.json._

private[saloon] object Main extends App {

  val config = ConfigFactory.load()

  implicit val system = ActorSystem("Bookshelf-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  import DefaultJsonProtocol._

  def format2SearchResults[A : JsonFormat, B : JsonFormat, C : JsonFormat, D : JsonFormat]
  (res: List[(A, B, C, D)]): JsValue =
    res.map {
      case (a, b, None, None) =>
        Map(
          "url" -> a.toJson,
          "title" -> b.toJson
        ).toJson
      case (a, b, c, None) =>
        Map(
          "url" -> a.toJson,
          "title" -> b.toJson,
          "description" -> c.toJson
        ).toJson
      case (a, b, None, d) =>
        Map(
          "url" -> a.toJson,
          "title" -> b.toJson,
          "image" -> d.toJson
        ).toJson
      case (a, b, c, d) =>
        Map(
          "url" -> a.toJson,
          "title" -> b.toJson,
          "description" -> c.toJson,
          "image" -> d.toJson
        ).toJson
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
                Map("results" -> JsArray()).toJson
              else
                Map(
                  "results" -> Map(
                    "category1" -> Map(
                      "name" -> "Authors".toJson,
                      "results" -> format2SearchResults(auth)
                    ).toJson,
                    "category2" -> Map(
                      "name" -> "Publications".toJson,
                      "results" -> format2SearchResults(pub)
                    ).toJson,
                    "category3" -> Map(
                      "name" -> "Titles".toJson,
                      "results" -> format2SearchResults(titl)
                    ).toJson
                  ).toJson
                ).toJson
            HttpResponse(entity = HttpEntity(ContentType(MediaTypes.`application/json`), json.compactPrint))
          }
        }
      }
    } ~
      pathSingleSlash {
        getFromFile("../static/index.html")
      } ~
      getFromDirectory("../static")


  val bind = Http().bindAndHandle(
    logRequestResult("Bookshelf", Logging.InfoLevel)(routes),
    config.getString("http.interface"),
    config.getInt("http.port")
  )

  println("Press ENTER to stop.")
  /*StdIn.readLine()
  bind
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())*/

}
