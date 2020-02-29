package scalandas.dataframe

import scalandas.dataframe.types.Types.DataType
import scalandas.dataframe.DataFrame
import cats.data.Validated.{Invalid, Valid}
import scalandas.dataframe.types.{Column, Schema}
import scala.util.matching.Regex

object check extends App {

 // (/\.:;`,\{\}\(|\) =
  val regex = "[:;.`,(){}\\[\\]|\n=\t*%&\b@#!~^<>?/\"' ]".r
  val name = regex.findFirstIn("""\""").isDefined  //.contains("(/|\\.|:|;|`|,|\\{|\\}|\\(|\\)|\n|\t|=| )")
  println(name)
 // val col1 = Column("colA;", -1, DataType.StringType, true)
 // println(col1)

 //new DataType("a")

  val col2 = Column("col2", 1, DataType.StringType, true)
  val col3 = Column("col3", 2, DataType.StringType, true)
  val col4 = Column("col4", 3, DataType.LongType, true)
 val col5 = Column("col4", 5, DataType.DoubleType, true)
 println(col5)

  val schema1 = Schema(col2, col3)
 val schema2 = Schema(Array(col2, col3))
 val schema3 = Schema.add(col2).add(col3)
 println(schema3)
 val schema4 = Schema().add(col2)
 //val schema5 = Schema().add(col4).add(col5) // fail


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
