package scalandas.dataframe

private class Row(values: Array[Any]) extends Serializable {

  // apply
 // def apply(name: String): Any = this.values
  def apply(i: Int): Any = this.values(i)

  // descriptive
  def size: Long = this.values.length

  // manipulate
  def map = ???
  def flatMap = ???
  def filter = ???
  def reduce = ???

  // get
  def toArray: Array[Any] = this.values


}


object Row {

  def apply(values: Any*): Row = new Row(values.toArray)

//  private def zipValueWithIndex(arr: Array[Any]): Array[(String, Any)] = {
//    var i = 0
//    for (value <- arr) yield {
//      i += 1
//      ("_" + i.toString, value)
//    }
//  }
}
