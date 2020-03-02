package scalandas.dataframe.validation

import scalandas.dataframe.types.Types.DataType

class ColumnValidatorInput(val name: String, val position: Int, val dataType: DataType, val nullable: Boolean) extends AbstractValidatorInput
