package scalandas.dataframe

case class Column(name: String, position: Int, valueType: ValueType, nullable: Boolean) {

  def getTypeAsString: String = valueType.valueTypeName
}
