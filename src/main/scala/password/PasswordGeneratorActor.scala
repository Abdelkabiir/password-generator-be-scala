package password

import akka.actor.{Actor, ActorLogging, Props}

import password.PasswordUtil._

final case class PasswordOptions(length: Int,
                                 numbers: Boolean,
                                 lowerCase: Boolean,
                                 upperCase: Boolean,
                                 ASCIISymbols: Boolean)

object PasswordGeneratorActor {
  final case class GeneratePassword(options: PasswordOptions)

  def props: Props = Props[PasswordGeneratorActor]
}

class PasswordGeneratorActor extends Actor with ActorLogging {
  import password.PasswordGeneratorActor._

  def receive: Receive = {
    case GeneratePassword(options: PasswordOptions) =>
      sender() ! generatePassword(options)
  }
}