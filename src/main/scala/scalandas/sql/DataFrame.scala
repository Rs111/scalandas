package scalandas.sql

import scalandas.sql.conversion.TypeRestriction

import scala.concurrent
import scalandas.sql.types.{Field, Schema, Types}
import scalandas.sql.conversion.ImplicitConversion._
import scalandas.sql.validation.dataframe.{DataFrameValidator, DataFrameValidatorInput}
import scalandas.sql.validation.ValidationOps._

class DataFrame private[sql](schema: Schema, rows: Array[Row]) {

  def this() {
    this(Schema(), Array[Row]())
  }

  def getSchema: Schema = this.schema
  def getRows: Array[Row] = this.rows

  def count: Long = this.rows.length

  def head(i: Int): DataFrame = {
    if (this.count < i)
      new DataFrame(this.schema, this.rows)
    else
      new DataFrame(this.schema, this.rows.slice(0, i))
  }
  def head: DataFrame = head(10)

  override def toString: String = this.rows.toList.map(row => row.toString).toString

  def show(i: Int = 10): Unit = {
    println("----------------------------------------------------")
    this.head(i).getRows.foreach(row => println("|" + row + "|"))
    println("----------------------------------------------------")
  }

  def printSchema: Unit = println(this.schema)

  def withColumn[T : TypeRestriction](name: String, value: => T): DataFrame = {
    new DataFrame(
      schema =
        this
          .schema
          .add(Field(name, this.schema.length + 1, value.toDataType(new GenericConverter(value)), true)),
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
