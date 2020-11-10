package users

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import model.User
import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.server.Directives._
import users.UserController.QueryUser

import scala.util.{Failure, Success}

object UserController {
  case class QueryUser(user: User)

  object UserJsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {

    implicit val userFormat = jsonFormat4(User.apply)
    implicit val userQueryFormat = jsonFormat1(QueryUser.apply)
  }
}

trait UserController extends UserService{

  import users.UserController.UserJsonProtocol._
  implicit def actorSystem: ActorSystem

  lazy val logger = Logging(actorSystem, classOf[UserController])

  lazy val usersRoutes: Route = pathPrefix("users") {
    get {
      path(Segment) { id =>
        onComplete(findUserById(id.toInt)) {
          _ match {
            case Success(user) =>
              logger.info(s"Got the user records given the user id ${id}")
              complete(StatusCodes.OK, user)
            case Failure(throwable) =>
              logger.error(s"Failed to get the user record given the user id ${id}")
              throwable match {
                case e: UserNotFoundException => complete(StatusCodes.NotFound, "No user found")
                case e: DuplicateUserFoundException => complete(StatusCodes.NotFound, "Dubious records found.")
                case _ => complete(StatusCodes.InternalServerError, "Failed to get the users.")
              }
          }
        }
      }
    } ~ post {
      path("query") {
        entity(as[QueryUser]) { q: User =>
          onComplete(findUser(q.id, q.userName, q.password , q.dateOfBirth)) {
            _ match {
              case Success(user) =>
                logger.info("Got the user records for the search query.")
                complete(StatusCodes.OK, user)
              case Failure(throwable) =>
                logger.error("Failed to get the user with the given query condition.")
                complete(StatusCodes.InternalServerError, "Failed to query the user.")
            }
          }
        }
      }
    }
  }

}
