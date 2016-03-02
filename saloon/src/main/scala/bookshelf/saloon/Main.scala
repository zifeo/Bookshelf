package bookshelf.saloon

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

private[saloon] object Main extends App {

  val config = ConfigFactory.load()

  implicit val system = ActorSystem("Bookshelf-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val routes =
    path("q") {
      get {
        complete {
          "hello"
        }
      }
    } ~
      pathSingleSlash {
        getFromFile("static/index.html")
      } ~
      getFromDirectory("static")


  val bindingFuture = Http().bindAndHandle(logRequestResult("Bookshelf", Logging.InfoLevel)(routes), config.getString("http.interface"), config.getInt("http.port"))

  println("Press ENTER to stop.")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

}
