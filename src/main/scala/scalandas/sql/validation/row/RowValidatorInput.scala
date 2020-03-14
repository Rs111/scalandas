package scalandas.sql.validation.row

import scalandas.sql.validation.AbstractValidatorInput

private[sql] class RowValidatorInput(val values: Array[Any]) extends AbstractValidatorInput
