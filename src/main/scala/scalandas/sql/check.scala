package scalandas.sql

import scalandas.sql.types.Types.DataType
import scalandas.sql.DataFrame
import cats.data.Validated.{Invalid, Valid}
import scalandas.sql.types.{Field, Schema}
import scala.util.matching.Regex

object check extends App {

 // (/\.:;`,\{\}\(|\) =
  val regex = "[:;.`,(){}\\[\\]|\n=\t*%&\b@#!~^<>?/\"' ]".r
  val name = regex.findFirstIn("""\""").isDefined  //.contains("(/|\\.|:|;|`|,|\\{|\\}|\\(|\\)|\n|\t|=| )")
  println(name)
 // val col1 = Field("colA;", -1, DataType.StringType, true)
 // println(col1)

 //new DataType("a")

  val col2 = Field("col2", 1, DataType.StringType, true)
  val col3 = Field("col3", 2, DataType.StringType, true)
  val col4 = Field("col4", 3, DataType.LongType, true)
 val col5 = Field("col4", 5, DataType.DoubleType, true)
 println(col5)

  val schema1 = Schema(col2, col3)
 val schema2 = Schema(Array(col2, col3))
 val schema3 = Schema.add(col2).add(col3)
 println(schema3)
 val schema4 = Schema().add(col2)
 //val schema5 = Schema().add(col4).add(col5) // fail
 val schema6 = Schema().add(col2).add(col4)
 val schema7 = new Schema().add(col2)

 val row1 = Row("a", 5.toLong)
 val row2 = Row("b", 6.toLong)
 val row3 = Row("c", 10)
 val row4 = Row("d", "17")
 val row5 = Row(Array.empty[Any])
 val row6 = Row()
 val row7 = new Row()

// println(row1 matches schema2)
//
//  row1
//    .toArray
//    .zipWithIndex
//    .map {
//     case (value, index) => value.toDataType
//    }
//   .foreach(println)
//
//   row1
//    .toArray
//    .zipWithIndex
//    .map {
//     case (value, index) => value.toDataType == schema2(index).getDataType
//    }
//   .foreach(println)


 val df1 = DataFrame(schema6, Array(row1, row2)).withColumn("test_col", 5.toLong)
 val df2 = DataFrame(schema6, row1, row2)
// val df3 = DataFrame(schema6, )
 println(df1.toString())
 println(row1)
 df1.show(1)
 df1.printSchema



//  val col2 = Field("col0", -1, DataType.StringType, true)
//  val col3 = Field("col.*0", 1, DataType.StringType, true)
//  val col4 = Field("col.*0", -5, DataType.StringType, true)
//
//  val x = col2 match {
//    case Valid(a) => "good"
//    case Invalid(a) => "bad"
//  }
//
//  println(x)
//
//  Seq(col1, col2, col3, col4).foreach(println)
//  val col2 = Field("col1", 1, DataType.IntType, true)
//  val col3 = Field("col2", 1, DataType.DoubleType, true)
//
//  val schema0 = Schema(Array(col1, col2, col3))
//  val schema4 = Schema(col1, col2, col3)
//  val schema5 = Schema.add(col1)
////  val schema1 = new Schema(Array(col1, col2, col3))
//  val schema2 = Schema().add(col1).add(col2)
//
//  val row0 = Row(Array("rad", 1, 2.0))
}
