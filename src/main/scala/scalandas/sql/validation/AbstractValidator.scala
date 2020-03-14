package scalandas.sql.validation

import cats.data.ValidatedNec

private[validation] trait AbstractValidator[I <: AbstractValidatorInput, R] {

  type E <: AbstractDomainValidation with HasErrorMessage
  type ValidationResult[A] = ValidatedNec[E,A]

  def validate(input: I): ValidationResult[R]
}

