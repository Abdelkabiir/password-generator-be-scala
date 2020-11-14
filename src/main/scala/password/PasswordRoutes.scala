package password

import scala.concurrent.duration._
import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask

import password.PasswordGeneratorActor.GeneratePassword

import scala.concurrent.Future

trait PasswordRoutes extends JsonSupport {

  implicit def system: ActorSystem
  def passwordGeneratorActor: ActorRef
  implicit lazy val passwordTimeout = Timeout(5.seconds)

  lazy val passwordRoutes: Route = pathPrefix("password") {
    pathEnd {
      post {
        entity(as [PasswordOptions]) {options =>
          val generatedPassword: Future[String] =
            (passwordGeneratorActor ? GeneratePassword(options)).mapTo[String]
           rejectEmptyResponse {
             complete(generatedPassword)
           }
        }
      }
    }
  }
}
