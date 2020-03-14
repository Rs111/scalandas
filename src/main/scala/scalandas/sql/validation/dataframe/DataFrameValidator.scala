package scalandas.sql.validation.dataframe

import cats.data.ValidatedNec
import cats.implicits._
import scalandas.sql.{DataFrame, Row}
import scalandas.sql.types.Schema
import scalandas.sql.validation._

trait DataFrameValidator extends AbstractValidator[DataFrameValidatorInput, DataFrame]{

  override type E = DataFrameDomainValidation with HasErrorMessage
  override type ValidationResult[A] = ValidatedNec[E, A]

  override def validate(input: DataFrameValidatorInput): ValidationResult[DataFrame] = {
    validateRowSchemaMatch(input.schema, input.rows)
      .map { case _ => new DataFrame(input.schema, input.rows)}
  }

  private def validateRowSchemaMatch(schema: Schema, rows: Array[Row]): ValidationResult[Array[Row]] = {
    if (rows.takeWhile(row => row matches schema).length == rows.length)
      rows.validNec
    else
       DataFrameTypeDoesNotMatchSchema.invalidNec
  }

  sealed trait DataFrameDomainValidation extends AbstractDomainValidation with HasErrorMessage

  case object DataFrameTypeDoesNotMatchSchema extends DataFrameDomainValidation {
    def errorMessage: String = "'Rows do not match Schema'"
  }
}

object DataFrameValidator extends DataFrameValidator