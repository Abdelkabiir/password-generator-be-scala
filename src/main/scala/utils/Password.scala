package utils

import constants.Constants
import model.PasswordOptions

object Password extends App {

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
    List()++
      Constants.optionsValues.Numbers ++
      Constants.optionsValues.LowerCaseLetters ++
      Constants.optionsValues.UpperCaseLetters ++
      Constants.optionsValues.NonAlphaAsciiSymbols
  }

  def generateRandomNumber(n: Int): Int = {
    Math.floor(Math.random() * n).toInt
  }

  /*
    val password = generatePassword(PasswordOptions(length = 9))
    println(password)
  */

}
