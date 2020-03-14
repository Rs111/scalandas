package scalandas.sql.types

import scala.reflect.ClassTag
// https://docs.scala-lang.org/overviews/scala-book/case-objects.html

//sealed class DataType[T : ClassTag] {
//  val scalaClass: Class[T] = classOf[T]
//  val typeName: String = scalaClass.toString
//  val classTag = ClassTag(scalaClass)
//}

//object DataType {
//  case object StringType extends DataType
//  case object IntType extends DataType
//  case object LongType extends DataType
//  case object DoubleType extends DataType
//  val valueTypes: Seq[DataType[_]] = Seq(StringType, IntType, LongType, DoubleType)
//}

object Types {

  private[sql] sealed class DataType private(val typeName: String)

  object DataType {

    case object StringType extends DataType("String")
    case object IntType extends DataType("Int")
    case object LongType extends DataType("Long")
    case object DoubleType extends DataType("Double")
  }
}
