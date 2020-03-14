package scalandas.sql.validation

import cats.data.Validated.{Invalid, Valid}
import cats.data.ValidatedNec

private[sql] object ValidationOps {

  implicit class ValidatedNecOps[E <: AbstractDomainValidation with HasErrorMessage, A](input: ValidatedNec[E, A]) {

    def getValidationOutput(implicit className: String): A = {
      input match {
        case Valid(output) => output
        case Invalid(err) =>
          throw new IllegalArgumentException(
            s"$className creation failed with errors:" +
              s"\n ${err.toChain.toList.map(_.errorMessage).mkString("\n ")}"
          )
      }
    }
  }
}
