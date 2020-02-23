package scalandas.dataframe.conversion

import scalandas.dataframe.types.Types.DataType


private[scalandas] object ImplicitConverters {

  implicit class DataTypeOps[T](value: T) {
    def toDataType(implicit converter: DataTypeConverter[T]): DataType =
      converter.convert(value)
  }

  implicit object AnyConverter extends DataTypeConverter[Any] {

    override def convert(value: Any): DataType = value match {
      case value: String => value.toDataType
      case value: Int => value.toDataType
      case value: Long => value.toDataType
      case value: Double => value.toDataType
    }
  }

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
}