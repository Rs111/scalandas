package scalandas.dataframe

import scala.concurrent

sealed class DataFrame(schema: Schema, rows: Array[Row]) {

  def withColumn() = ???
}

object DataFrame {

  def apply(schema: Schema, rows: Row*): DataFrame = {

    new DataFrame(schema, rows.toArray)
  }

  private def checkSchemaAgainstRows(schema: Schema, rows: Array[Row]): Unit = {

    rows
      .

  }

  private def checkSchemaAgainstOneRow(schema: Schema, row: Row): Boolean = {

    val columnArray = schema.toArray

    row
      .toArray
      .zipWithIndex
      .map {
        case (value: Any, position: Int) => {
          val columnType = columnArray(position).valueType

          value match {

          }
        }
      }

  }
}
