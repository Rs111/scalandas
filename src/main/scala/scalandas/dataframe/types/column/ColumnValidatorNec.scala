package scalandas.dataframe.types.column

import cats.data._
import cats.implicits._
import scalandas.dataframe.types.Types.DataType

sealed trait ColumnValidatorNec {

  type ValidationResult[A] = ValidatedNec[ColumnDomainValidation, A]

  val columnLimit = 10000

  def validateColumn(name: String, position: Int, dataType: DataType, nullable: Boolean): ValidationResult[Column] = {

    (validateName(name), validatePosition(position))
      .mapN((name, position) => new Column(name, position, dataType, nullable))
  }

  private def validateName(name: String): ValidationResult[String] = {

    if (name == null) ColumnNameIsNull.invalidNec
    else if ("[:;.`,(){}\\[\\]|\n=\t*%&\b@#!~^<>?/\"' ]".r.findFirstIn(name).isDefined) ColumnNameContainsInvalidCharacters.invalidNec //TODO add backslash
    else if ("[A-Z]".r.findFirstIn(name).isDefined) ColumnNameContainsUpperCaseCharacters.invalidNec
    else name.validNec
  }

  private def validatePosition(position: Int): ValidationResult[Int] = {
    if (position < 0) ColumnPositionIsNegative.invalidNec
    else if (position > columnLimit) ColumnPositionMoreThanColLimit.invalidNec
    else position.validNec
  }

  sealed trait ColumnDomainValidation {
    def errorMessage: String //TODO add messages
  }

  case object ColumnNameIsNull extends ColumnDomainValidation {
    def errorMessage: String = "'ColumnNameIsNull: name parameter is null'"
  }
  case object ColumnNameContainsInvalidCharacters extends ColumnDomainValidation {
    def errorMessage: String = "'ColumnNameContainsInvalidCharacters: name parameter is null'"
  }
  case object ColumnNameContainsUpperCaseCharacters extends ColumnDomainValidation {
    def errorMessage: String = "'ColumnNameContainsUpperCaseCharacters: name parameter is null'"
  }
  case object ColumnPositionIsNegative extends ColumnDomainValidation {
    def errorMessage: String = "'ColumnPositionIsNegative: name parameter is null'"
  }
  case object ColumnPositionMoreThanColLimit extends ColumnDomainValidation {
    def errorMessage: String = "'ColumnPositionMoreThanColLimit: name parameter is null'"
  }
}

object ColumnValidatorNec extends ColumnValidatorNec
