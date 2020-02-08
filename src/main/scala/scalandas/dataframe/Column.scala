package scalandas.dataframe

class Column(name: String, position: Int, valueType: ValueType) {

  def getTypeAsString: String = valueType.valueTypeName
}
