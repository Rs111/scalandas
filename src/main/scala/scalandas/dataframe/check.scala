package scalandas.dataframe

import scalandas.dataframe.types.Schema
import scalandas.dataframe.types.Types.DataType
import scalandas.dataframe.{DataFrame, Row}
import scalandas.dataframe.types.column.Column
import cats.data.Validated.{Valid, Invalid}
import scala.util.matching.Regex

object check extends App {

 // (/\.:;`,\{\}\(|\) =
  val regex = "[:;.`,(){}\\[\\]|\n=\t*%&\b@#!~^<>?/\"' ]".r
  val name = regex.findFirstIn("""\""").isDefined  //.contains("(/|\\.|:|;|`|,|\\{|\\}|\\(|\\)|\n|\t|=| )")
  println(name)
  val col1 = Column("colA", -1, DataType.StringType, true)
  println(col1)
//  val col2 = Column("col0", -1, DataType.StringType, true)
//  val col3 = Column("col.*0", 1, DataType.StringType, true)
//  val col4 = Column("col.*0", -5, DataType.StringType, true)
//
//  val x = col2 match {
//    case Valid(a) => "good"
//    case Invalid(a) => "bad"
//  }
//
//  println(x)
//
//  Seq(col1, col2, col3, col4).foreach(println)
//  val col2 = Column("col1", 1, DataType.IntType, true)
//  val col3 = Column("col2", 1, DataType.DoubleType, true)
//
//  val schema0 = Schema(Array(col1, col2, col3))
//  val schema4 = Schema(col1, col2, col3)
//  val schema5 = Schema.add(col1)
////  val schema1 = new Schema(Array(col1, col2, col3))
//  val schema2 = Schema().add(col1).add(col2)
//
//  val row0 = Row(Array("rad", 1, 2.0))
}
