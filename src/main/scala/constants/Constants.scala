package constants

object Constants {

  object optionsValues {
    val Numbers = (1 to 9)
    val LowerCaseLetters = ('a' to 'z')
    val UpperCaseLetters = ('A' to 'Z')
    val NonAlphaAsciiSymbols = ('!' to '/') ++:
      (':' to '@') ++:
      ('[' to '`') ++:
      ('{' to '~')
  }

}
