package scalandas.sql

import scala.concurrent
import scalandas.sql.types.{Field, Schema, Types}
import scalandas.sql.conversion.ImplicitConverters._
import scalandas.sql.validation.dataframe.{DataFrameValidator, DataFrameValidatorInput}
import scalandas.sql.validation.ValidationOps._

class DataFrame private[sql](schema: Schema, rows: Array[Row]) {

  def this() {
    this(Schema(), Array[Row]())
  }

  def withColumn[A](name: String, value: => A): DataFrame = {
    new DataFrame(
      schema =
        this
          .schema
          .add(Field(name, this.schema.length + 1, value.toDataType, true)),
      rows = this.rows.map(row => row.add(value))
    )
  }

  def withRow(row: Row): DataFrame = new DataFrame(this.schema, this.rows :+ row)
}

object DataFrame {

  implicit val className: String = "DataFrame"

  def apply(schema: Schema, rows: Array[Row]): DataFrame = {
    DataFrameValidator
      .validate(new DataFrameValidatorInput(schema, rows))
      .getValidationOutput
  }

  def apply(): DataFrame = new DataFrame()

  def apply(schema: Schema, rows: Row*): DataFrame = apply(schema, rows.toArray)

  def apply(schema: Schema): DataFrame = ??? //TODO empty DF with schema

  def apply(rows: Array[Row]): DataFrame = ??? //TODO DF with inferred schema
  def apply(rows: Row*): DataFrame = ??? //TODO DF with inferred schema
}
