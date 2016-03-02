package bookshelf.saloon

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

object Main extends App {

  val config = ConfigFactory.load()

  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val routes =

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))

}
