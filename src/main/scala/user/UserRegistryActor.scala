package user

import akka.actor.{Actor, ActorLogging, Props}

final case class User(id:Int, userName: String, password: String, dateOfBirth: String)
final case class Users(users: Seq[User])

object UserRegistryActor {
  final case class ActionPerformed(description: String)
  final case object GetUsers
  final case class CreateUser(user: User)
  final case class GetUserById(id: Int)
  final case class GetUserByName(name: String)
  final case class DeleteUser(id: Int)

  def props: Props = Props[UserRegistryActor]
}


class UserRegistryActor extends Actor with ActorLogging {
  import UserRegistryActor._

  var users = Set.empty[User]

  def receive: Receive = {
    case GetUsers =>
      sender() ! Users(users.toSeq)
    case CreateUser(user) =>
      users += user
      sender() ! ActionPerformed(s"User ${user.userName} created.")
    case GetUserById(id) =>
      sender() ! users.find(_.id == id)
    case GetUserByName(userName) =>
      sender() ! users.find(_.userName == userName)
    case DeleteUser(id) =>
      users.find(_.id == id) foreach { user => users -= user }
      sender() ! ActionPerformed(s"User ${id} deleted.")
  }
}
