package scalandas.sql.validation.field

import scalandas.sql.types.Types.DataType
import scalandas.sql.validation.AbstractValidatorInput

private[sql] class FieldValidatorInput(val name: String, val position: Int, val dataType: DataType, val nullable: Boolean) extends AbstractValidatorInput
