package scalandas.dataframe.validation

import cats.data.ValidatedNec

private[validation] trait AbstractValidator {

  type E = AbstractDomainValidation with HasErrorMessage
  type ValidationResult[_] = ValidatedNec[E, _]

  def validate[I <: AbstractValidatorInput](input: I): ValidationResult[_]
}

