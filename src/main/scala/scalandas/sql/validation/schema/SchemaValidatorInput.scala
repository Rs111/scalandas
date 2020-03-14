package scalandas.sql.validation.schema

import scalandas.sql.types.Field
import scalandas.sql.validation.AbstractValidatorInput

private[sql] class SchemaValidatorInput(val fields: Array[Field]) extends AbstractValidatorInput
