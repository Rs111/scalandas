package scalandas.sql.conversion

import scalandas.sql.types.Types.DataType

private[sql] object ImplicitConversion {

  implicit class DataTypeOps[T : TypeRestriction](value: T) {
    def toDataType(implicit converter: DataTypeConverter[T]): DataType = converter.convert(value)
  }

  implicit object AnyConverter extends GenericConverter[Any]

  implicit object StringConverter extends DataTypeConverter[String] {

    override def convert(value: String): DataType = DataType.StringType
  }

  implicit object IntegerConverter extends DataTypeConverter[Int] {

    override def convert(value: Int): DataType = DataType.IntType
  }

  implicit object LongConverter extends DataTypeConverter[Long] {

    override def convert(value: Long): DataType = DataType.LongType
  }

  implicit object DoubleConverter extends DataTypeConverter[Double] {

    override def convert(value: Double): DataType = DataType.DoubleType
  }

  class GenericConverter[T : TypeRestriction](value: T) extends DataTypeConverter[T] {
    override def convert(value: T): DataType = value match {
      case value: String => StringConverter.convert(value)
      case value: Int => IntegerConverter.convert(value)
      case value: Long => LongConverter.convert(value)
      case value: Double => DoubleConverter.convert(value)
    }
  }
}
