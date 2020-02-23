package scalandas.dataframe.types

package object column {

  val invalidCharacters = "[:;.`,(){}\\[\\]|\n=\t*%&\b@#!~^<>?/\"' ]" //TODO add backslash
  val columnLimit = 10000
}
