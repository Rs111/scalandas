package scalandas.sql.conversion

import scalandas.sql.types.Types.DataType

private[scalandas] trait DataTypeConverter[T] {
  def convert(value: T): DataType
}