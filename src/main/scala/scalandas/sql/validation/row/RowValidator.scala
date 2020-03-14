package scalandas.sql.validation.row

import cats.data.ValidatedNec
import cats.implicits._
import scalandas.sql.Row
import scalandas.sql.validation._

trait RowValidator extends AbstractValidator[RowValidatorInput, Row] {

  override type E = RowDomainValidation with HasErrorMessage
  override type ValidationResult[A] = ValidatedNec[E, A]

  //TODO decide whether/how to use this
  override def validate(input: RowValidatorInput): ValidationResult[Row] = { new Row(input.values).validNec
//    (validateName(input.name), validatePosition(input.position))
//      .mapN { case _ => new Field(input.name, input.position, input.dataType, input.nullable)}
  }

  sealed trait RowDomainValidation extends AbstractDomainValidation with HasErrorMessage

  case object RowHasUnsupportedType extends RowDomainValidation {
    def errorMessage: String = "'RowNameIsNull: '"
  }

}

object RowValidator extends RowValidator