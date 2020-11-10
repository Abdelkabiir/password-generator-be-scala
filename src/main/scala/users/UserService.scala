package users

import akka.actor.ActorSystem
import model.User
import utils.UsersList
import utils.UsersList.MList

import scala.concurrent.Future


object UsersServiceContext {
  lazy val actorSystem = ActorSystem("UsersServiceContext")
  lazy val scheduler = actorSystem.scheduler
  implicit lazy val executionContext = actorSystem.dispatcher
}


trait UserService {

  import akka.pattern.after
  import scala.concurrent.duration._
  import users.UsersServiceContext._

  class UserNotFoundException extends Throwable("No user match found")

  class DuplicateUserFoundException extends Throwable("Multiple users match found")


  // Emulate DB
  def fetchUsers(): Future[MList[User]] = {
    val randomDuration = (Math.random() * 5 + 3).toInt.seconds
    after(randomDuration, scheduler) {
      Future {
        UsersList.users
      }
    }
  }

  def findAllUsers() = fetchUsers().map(result => result)

  def findUser(id: Int, userName: String, password: String, dateOfBirth: String) = fetchUsers()
    .map(result => {
      result.filter(user => user.userName == userName &&
        user.password == password &&
        user.dateOfBirth == dateOfBirth &&
        user.id == id
      )
    })

  def findUserByUserName(userName: String) = fetchUsers().map(result => {
    val filteredResult = result.filter(user => user.userName == userName)
    if (filteredResult.isEmpty) {
      throw new UserNotFoundException
    } else if (filteredResult.length > 1) {
      throw new DuplicateUserFoundException
    } else {
      filteredResult(0)
    }
  })

  def findUserById(id: Int) = fetchUsers().map(result => {
    val filteredResult = result.filter(user => user.id == id)
    if (filteredResult.isEmpty) {
      throw new UserNotFoundException
    } else if (filteredResult.length > 1) {
      throw new DuplicateUserFoundException
    } else {
      filteredResult(0)
    }
  })

  def createUser(user: User) = fetchUsers().map(result => {
    result :+ user
  })
}
