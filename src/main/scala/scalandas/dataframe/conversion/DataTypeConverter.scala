package scalandas.dataframe.conversion

import scalandas.dataframe.types.Types.DataType

private[scalandas] trait DataTypeConverter[T] {
  def convert(value: T): DataType
}