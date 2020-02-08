package scalandas.dataframe

case class Value(name: String, value: Any, valueType: ValueType) {
  def getTypeAsString: String = valueType.valueTypeName
}
