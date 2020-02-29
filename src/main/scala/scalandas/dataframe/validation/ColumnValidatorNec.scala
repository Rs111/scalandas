package scalandas.dataframe.validation

import cats.data.ValidatedNec
import cats.implicits._
import scalandas.dataframe.types.Column
import scalandas.dataframe.types.Types.DataType

private[dataframe] sealed trait ColumnValidatorNec {

  type ValidationResult[A] = ValidatedNec[ColumnDomainValidation, A]

  private val invalidCharacters = "[:;.`,(){}\\[\\]|\n=\t*%&\b@#!~^<>?/\"' ]" //TODO add backslash; change logic, make blacklist
  private val columnLimit = 10000

  def validateColumn(name: String, position: Int, dataType: DataType, nullable: Boolean): ValidationResult[Column] = {
    (validateName(name), validatePosition(position))
      .mapN { case _ => new Column(name, position, dataType, nullable)}
  }

  private def validateName(name: String): ValidationResult[String] = { //TODO return refinement of string
    (validateNameNonNull(name), validateNameInvalidChars(name), validateNameUppercaseChars(name))
      .mapN { case _ => name}
  }

  private def validateNameNonNull(name: String): ValidationResult[String] = {
    if (name == null) ColumnNameIsNull.invalidNec
    else name.validNec
  }

  private def validateNameInvalidChars(name: String): ValidationResult[String] = {
    if (invalidCharacters.r.findFirstIn(name).isDefined) ColumnNameContainsInvalidCharacters.invalidNec
    else name.validNec
  }

  private def validateNameUppercaseChars(name: String): ValidationResult[String] = {
    if ("[A-Z]".r.findFirstIn(name).isDefined) ColumnNameContainsUpperCaseCharacters.invalidNec
    else name.validNec
  }

  private def validatePosition(position: Int): ValidationResult[Int] = {
    if (position < 0) ColumnPositionIsNegative.invalidNec
    else if (position > columnLimit) ColumnPositionMoreThanColLimit.invalidNec
    else position.validNec
  }

  sealed trait ColumnDomainValidation extends HasErrorMessage

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



/*
  private def validateName(name: String): ValidationResult[String] = { //TODO return refinement of string
    if (name == null) ColumnNameIsNull.invalidNec
    else {
      lazy val nameContainsInvalidChars = invalidCharacters.r.findFirstIn(name).isDefined
      lazy val nameContainsUpperCaseChars = "[A-Z]".r.findFirstIn(name).isDefined
      if (nameContainsInvalidChars & !nameContainsUpperCaseChars)
        ColumnNameContainsInvalidCharacters.invalidNec
      else if (!nameContainsInvalidChars & nameContainsUpperCaseChars)
        ColumnNameContainsUpperCaseCharacters.invalidNec
      else if (nameContainsInvalidChars & nameContainsUpperCaseChars)
        Semigroup[ValidationResult[String]]
          .combine(ColumnNameContainsInvalidCharacters.invalidNec, ColumnNameContainsUpperCaseCharacters.invalidNec)
      else name.validNec
    }
  }
 */