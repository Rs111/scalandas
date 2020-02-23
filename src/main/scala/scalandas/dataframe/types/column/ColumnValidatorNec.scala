package scalandas.dataframe.types.column

import cats.data.ValidatedNec
import cats.implicits._
import scalandas.dataframe.types.Types.DataType

sealed trait ColumnValidatorNec {

  type ValidationResult[A] = ValidatedNec[ColumnDomainValidation, A]

  def validateColumn(name: String, position: Int, dataType: DataType, nullable: Boolean): ValidationResult[Column] = {

    (validateName(name), validatePosition(position))
      .mapN((name, position) => new Column(name, position, dataType, nullable))
  }

  private def validateName(name: String): ValidationResult[String] = { //TODO break up to parallelize

    if (name == null) ColumnNameIsNull.invalidNec
    else if (invalidCharacters.r.findFirstIn(name).isDefined) ColumnNameContainsInvalidCharacters.invalidNec
    else if ("[A-Z]".r.findFirstIn(name).isDefined) ColumnNameContainsUpperCaseCharacters.invalidNec
    else name.validNec
  }

  private def validatePosition(position: Int): ValidationResult[Int] = { //TODO break up to parallelize
    if (position < 0) ColumnPositionIsNegative.invalidNec
    else if (position > columnLimit) ColumnPositionMoreThanColLimit.invalidNec
    else position.validNec
  }

  sealed trait ColumnDomainValidation {
    def errorMessage: String
  }

  case object ColumnNameIsNull extends ColumnDomainValidation {
    def errorMessage: String = "'ColumnNameIsNull: name parameter is null'"
  }
  case object ColumnNameContainsInvalidCharacters extends ColumnDomainValidation {
    def errorMessage: String = s"'ColumnNameContainsInvalidCharacters: name parameter contains one or more of $invalidCharacters'"
  }
  case object ColumnNameContainsUpperCaseCharacters extends ColumnDomainValidation {
    def errorMessage: String = "'ColumnNameContainsUpperCaseCharacters: name contains an upper-case character'"
  }
  case object ColumnPositionIsNegative extends ColumnDomainValidation {
    def errorMessage: String = "'ColumnPositionIsNegative: position parameter is negative'"
  }
  case object ColumnPositionMoreThanColLimit extends ColumnDomainValidation {
    def errorMessage: String = s"'ColumnPositionMoreThanColLimit: position parameter is greater than column limit $columnLimit'"
  }
}

object ColumnValidatorNec extends ColumnValidatorNec
