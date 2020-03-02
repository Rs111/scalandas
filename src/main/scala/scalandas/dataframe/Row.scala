package scalandas.dataframe

class Row private[dataframe] (values: Array[Any]) extends Serializable {

  def apply(i: Int): Any = this.values(i)

  // descriptive
  def length: Int = this.values.length

  def map = ???
  def flatMap = ???
  def filter = ???
  def reduce = ???

  // get
  def toArray: Array[Any] = this.values
}


object Row {

  def apply(values: Array[Any]): Row = new Row(values)
  def apply(values: Any*): Row = new Row(values.toArray)

//  private def zipValueWithIndex(arr: Array[Any]): Array[(String, Any)] = {
//    var i = 0
//    for (value <- arr) yield {
//      i += 1
//      ("_" + i.toString, value)
//    }
//  }
}
