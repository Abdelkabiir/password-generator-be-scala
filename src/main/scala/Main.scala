import akka.actor.ActorSystem
import akka.http.javadsl.server.Route
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.POST
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpHeader, HttpRequest, HttpResponse, Uri}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("passwordGeneratorServer")

}
