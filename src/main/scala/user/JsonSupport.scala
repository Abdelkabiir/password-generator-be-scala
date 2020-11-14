package user

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import user.UserRegistryActor.ActionPerformed

trait JsonSupport extends SprayJsonSupport {
  import spray.json.DefaultJsonProtocol._

  implicit val userJsonFormat = jsonFormat4(User)
  implicit val usersJsonFormat = jsonFormat1(Users)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}