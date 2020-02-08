package scalandas.dataframe

class Schema(columns: Array[Column]) {

  def toArray: Array[Column] = this.columns
}
