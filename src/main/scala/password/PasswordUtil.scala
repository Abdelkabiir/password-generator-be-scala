package password

object PasswordUtil {

  val numbers = (1 to 9)
  val lowerCaseLetters = ('a' to 'z')
  val upperCaseLetters = ('A' to 'Z')
  val nonAlphaAsciiSymbols = ('!' to '/') ++:
    (':' to '@') ++:
    ('[' to '`') ++:
    ('{' to '~')

  def generatePassword(passwordOptions: PasswordOptions): String = {
    var result: String = ""
    val optionsSet = generateOptionsSet(passwordOptions)
    for (i <- 0 until passwordOptions.length) {
      val randomNumber = generateRandomNumber(optionsSet.length)
      result += optionsSet(randomNumber)
    }
    result
  }

  def generateOptionsSet(options: PasswordOptions) = {
    List() ++ numbers ++ lowerCaseLetters ++ upperCaseLetters ++ nonAlphaAsciiSymbols
  }

  def generateRandomNumber(n: Int): Int = {
    Math.floor(Math.random() * n).toInt
  }
}