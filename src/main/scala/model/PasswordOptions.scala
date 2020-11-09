package model

case class PasswordOptions(
                            length: Int,
                            numbers: Boolean = true,
                            lowerCase: Boolean = true,
                            upperCase: Boolean = true,
                            ASCIISymbols: Boolean = true
                          )
