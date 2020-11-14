import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

import password.{PasswordGeneratorActor, PasswordRoutes}
import user.{UserRegistryActor, UserRoutes}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

object Main extends App with UserRoutes with PasswordRoutes {
  implicit val system: ActorSystem = ActorSystem("mainActor")
  implicit val ec: ExecutionContext = system.dispatcher

  val userRegistryActor: ActorRef = system.actorOf(UserRegistryActor.props, "userRegistryActor")
  val passwordGeneratorActor: ActorRef = system.actorOf(PasswordGeneratorActor.props, "passwordGeneratorActor")

  lazy val routes: Route = concat(
    userRoutes,
    passwordRoutes
  )

  val serverBinding: Future[Http.ServerBinding] = Http().newServerAt("localhost", 9090).bind(routes)

  serverBinding.onComplete {
    case Success(bound) =>
      println(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
    case Failure(e) =>
      Console.err.println(s"Server could not start!")
      e.printStackTrace()
      system.terminate()
  }

  Await.result(system.whenTerminated, Duration.Inf)
}