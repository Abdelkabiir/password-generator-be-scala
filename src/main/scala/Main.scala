import akka.actor.ActorSystem
import akka.http.javadsl.server.Route
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.POST
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpHeader, HttpRequest, HttpResponse, Uri}
import akka.stream.scaladsl.Sink
import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("passwordGeneratorServer")
  implicit val executionContext: ExecutionContext = system.dispatcher

  lazy val routes: Route = ???
  val serverSource = Http()
    .newServerAt("localhost", 3002)
    .connectionSource()

  val corsHeader = RawHeader("Access-Control-Allow-Origin", "*")
  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(POST, Uri.Path("/session"), _, _, _) =>
      val res = HttpResponse(200).withHeaders(corsHeader)
      println(res)
      res
    case HttpRequest(POST, Uri.Path("/password"), _, _, _) =>
      HttpResponse(entity = HttpEntity("Ok"))
    case _ => HttpResponse(404, entity = "You requested a wrong route")
  }

  val bindingFuture: Future[Http.ServerBinding] =
    serverSource.to(Sink.foreach { connection =>
      println("New connection from " + connection.remoteAddress)
      connection handleWithSyncHandler (requestHandler)
    }).run()

  println("server running at : http://localhost:3002")
  scala.io.StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

}
