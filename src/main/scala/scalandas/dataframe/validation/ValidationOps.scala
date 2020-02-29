package scalandas.dataframe.validation

import cats.data.Validated.{Invalid, Valid}
import cats.data.ValidatedNec

private[dataframe] object ValidationOps {

  implicit class ValidatedNecOps[E <: HasErrorMessage, A](input: ValidatedNec[E, A]) {

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
