package scalandas.dataframe

import scala.concurrent
import scalandas.dataframe.conversion.ImplicitConverters._
import scalandas.dataframe.types.Schema

class DataFrame private (schema: Schema, rows: Array[Row]) {

  def withColumn() = ???
}

object DataFrame {

  def apply(schema: Schema, rows: Row*): DataFrame = {

    new DataFrame(schema, rows.toArray)
  }

  private def checkSchemaAgainstRows(schema: Schema, rows: Array[Row]): Unit = { //TODO use validation class

    rows.foreach(row => checkSchemaAgainstOneRow(schema, row))
  }

  private def checkSchemaAgainstOneRow(schema: Schema, row: Row): Unit = { //TODO use validation class

    row
      .toArray
      .zip(schema.toArray)
      .foreach{
        case (value, column) => throwIfFalse(value.toDataType == column.getDataType)
      }
  }

  private def throwIfFalse(b: Boolean): Boolean = {

    if (b) true
    else throw new RuntimeException("") //TODO use validation class
  }
}
