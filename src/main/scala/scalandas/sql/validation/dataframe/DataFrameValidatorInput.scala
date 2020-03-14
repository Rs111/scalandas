package scalandas.sql.validation.dataframe

import scalandas.sql.Row
import scalandas.sql.types.Schema
import scalandas.sql.validation.AbstractValidatorInput

private[sql] class DataFrameValidatorInput(val schema: Schema, val rows: Array[Row]) extends AbstractValidatorInput
