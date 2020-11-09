package users

import model.User
import utils.UsersList

trait UserService {

  class UserNotFoundException extends Throwable("No user match found")
  class DuplicateUserFoundException extends Throwable("Multiple users match found")


  def findAllUsers(): UsersList.MList[User] = {
    UsersList.users
  }

  def findUser(id: Int, userName: String, password: String, dateOfBirth: String) = {
    val res = UsersList.users.find(
      u => u.userName == userName &&
        u.id == id &&
        u.password == password
        && u.dateOfBirth == dateOfBirth
    )

    if (res isDefined) {
      res
    } else {
      throw new UserNotFoundException
    }
  }

  def findUserByUserName(userName: String) = {
    UsersList.users.find(user => user.userName == userName)
  }

  def findUserById(id: Int): Option[User] = {
    val res = UsersList.users.find(user => user.id == id)
    if (res isDefined) {
      res
    } else {
      throw new UserNotFoundException
    }
  }

  def createUser(user: User): Unit = {
    UsersList.users :+ user
  }
}
