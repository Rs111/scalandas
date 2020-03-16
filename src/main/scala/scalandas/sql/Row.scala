package scalandas.sql

import scalandas.sql.types.Schema
import scalandas.sql.conversion.ImplicitConversion._

class Row private[sql](values: Array[Any]) extends Serializable {

  def this() {
    this(Array[Any]())
  }

  def apply(i: Int): Any = this.values(i)

  // descriptive
  def length: Int = this.values.length

  override def toString: String = "Row" + values.mkString("(", ", ", ")")

  def map = ???
  def flatMap = ???
  def filter = ???
  def reduce = ???

  // get
  def toArray: Array[Any] = this.values

  // add
  def add(value: Any): Row = new Row(this.values :+ value)

  //
  def matches(schema: Schema): Boolean = {
    this
      .toArray
        .zipWithIndex
        .forall {
          case (value, index) => value.toDataType == schema(index).getDataType
        }
  }
}


object Row {

  def apply(): Row = new Row()
  def apply(values: Array[Any]): Row = new Row(values)
  def apply(values: Any*): Row = new Row(values.toArray)
}
