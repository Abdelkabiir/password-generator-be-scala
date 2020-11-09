package utils

import model.User
import java.util.Date

import scala.collection.mutable.ArrayBuffer

object UsersList {

  type MList[A] = scala.collection.mutable.Buffer[A]

  val users: MList[User] = ArrayBuffer(
    User(id = 1, userName = "Abdel", password = "1234", dateOfBirth = new Date()),
    User(id = 2, userName = "Abdelkabir", password = "4321", dateOfBirth = new Date()),
    User(id = 3, userName = "Abdelkabir watil", password = "2413", dateOfBirth = new Date()),
  )
}
