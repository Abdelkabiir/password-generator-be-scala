package password

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

trait JsonSupport extends SprayJsonSupport {
  import spray.json.DefaultJsonProtocol._

  implicit val passwordOptionsJsonFormat = jsonFormat5(PasswordOptions)
}
