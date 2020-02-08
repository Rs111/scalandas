package scalandas.dataframe

sealed class ValueType(val valueTypeName: String)

object ValueType {
  case object StringVal extends ValueType(valueTypeName = "String")
  case object IntVal extends ValueType(valueTypeName = "Int")
  case object LongVal extends ValueType(valueTypeName = "Long")
  case object DoubleVal extends ValueType(valueTypeName = "Double")
  val valueTypes: Seq[ValueType] = Seq(StringVal, IntVal, LongVal, DoubleVal)
}
