package users

import model.User
import utils.UsersList

trait UserService {

  def findAll(): UsersList.MList[User] = {
    UsersList.users
  }

  def findByUserName(userName: String): Option[User] = {
    UsersList.users.find(user => user.userName == userName)
  }

  def findById(id: Int): Option[User] = {
    UsersList.users.find(user => user.id == id)
  }

  def create(user: User): Unit = {
    UsersList.users :+ user
  }
}
