package user

import scala.concurrent.Future
import scala.concurrent.duration._

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.util.Timeout

import user.UserRegistryActor.{ActionPerformed, CreateUser, DeleteUser, GetUserById, GetUsers}

trait UserRoutes extends JsonSupport {

  implicit def system: ActorSystem
  lazy val log = Logging(system, classOf[UserRoutes])
  def userRegistryActor: ActorRef
  implicit lazy val timeout = Timeout(5.seconds)

  lazy val userRoutes: Route = pathPrefix("users") {
    concat(
      pathEnd {
        concat(
          get {
            val users: Future[Users] =
              (userRegistryActor ? GetUsers).mapTo[Users]
            complete(users)
          },
          post {
            entity(as[User]) { user =>
              val userCreated: Future[ActionPerformed] =
                (userRegistryActor ? CreateUser(user)).mapTo[ActionPerformed]
              onSuccess(userCreated) { performed =>
                log.info("Created user [{}]: {}", user.userName, performed.description)
                complete((StatusCodes.Created, performed))
              }
            }
          }
        )
      },
      path(Segment) { id =>
        concat(
          get {
            val maybeUser: Future[Option[User]] =
              (userRegistryActor ? GetUserById(id.toInt)).mapTo[Option[User]]
            rejectEmptyResponse {
              complete(maybeUser)
            }
          },
          delete {
            val userDeleted: Future[ActionPerformed] =
              (userRegistryActor ? DeleteUser(id.toInt)).mapTo[ActionPerformed]
            onSuccess(userDeleted) { performed =>
              log.info("Deleted user [{}]: {}", id, performed.description)
              complete((StatusCodes.OK, performed))
            }
          }
        )
      }
    )
  }
}