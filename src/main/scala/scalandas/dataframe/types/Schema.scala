package scalandas.dataframe.types

import scalandas.dataframe.validation.SchemaValidatorNec
import scalandas.dataframe.validation.ValidationOps._

class Schema private[dataframe] (columns: Array[Column]) {

  def this() {
    this(Array[Column]())
  }

  def add(column: Column): Schema = Schema.apply(this.columns :+ column)

  def toArray: Array[Column] = this.columns

  //TODO add a nice print
}

object Schema {

  implicit val className: String = "Schema"

  def apply(columns: Array[Column]): Schema = {
    SchemaValidatorNec
      .validateSchema(columns)
      .getValidationOutput
  }

  def apply(columns: Column*): Schema = apply(columns.toArray)

  def add(column: Column): Schema = Schema(Array(column))
}
