package scalandas.dataframe.validation

import cats.data.ValidatedNec
import cats.implicits._
import scalandas.dataframe.Row

trait RowValidator extends AbstractValidator {

  override type ValidationResult[A] = ValidatedNec[RowDomainValidation, A]

  //TODO decide whether/how to use this
  override def validate[I <: RowValidatorInput](input: I): ValidationResult[Row] = { new Row(input.values).validNec
//    (validateName(input.name), validatePosition(input.position))
//      .mapN { case _ => new Column(input.name, input.position, input.dataType, input.nullable)}
  }

  sealed trait RowDomainValidation extends AbstractDomainValidation with HasErrorMessage

  case object RowHasUnsupportedType extends RowDomainValidation {
    def errorMessage: String = "'RowNameIsNull: '"
  }

}

object RowValidator extends RowValidator