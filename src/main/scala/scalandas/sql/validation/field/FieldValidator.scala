package scalandas.sql.validation.field

import cats.data.ValidatedNec
import cats.implicits._
import scalandas.sql.types.Field
import scalandas.sql.validation._

private[sql] sealed trait FieldValidator extends AbstractValidator[FieldValidatorInput, Field] {

  private val invalidCharacters = "[:;.`,(){}\\[\\]|\n=\t*%&\b@#!~^<>?/\"' ]" //TODO add backslash; change logic, make blacklist
  private val columnLimit = 10000

  override type E = FieldDomainValidation with HasErrorMessage
  override type ValidationResult[A] = ValidatedNec[E, A]

  override def validate(input: FieldValidatorInput): ValidationResult[Field] = {
    (validateName(input.name), validatePosition(input.position))
      .mapN { case _ => new Field(input.name, input.position, input.dataType, input.nullable)}
  }

  private def validateName(name: String): ValidationResult[String] = { //TODO return refinement of string
    (validateNameNonNull(name), validateNameInvalidChars(name), validateNameUppercaseChars(name))
      .mapN { case _ => name}
  }

  private def validateNameNonNull(name: String): ValidationResult[String] = {
    if (name == null) FieldNameIsNull.invalidNec
    else name.validNec
  }

  private def validateNameInvalidChars(name: String): ValidationResult[String] = {
    if (invalidCharacters.r.findFirstIn(name).isDefined) FieldNameContainsInvalidCharacters.invalidNec
    else name.validNec
  }

  private def validateNameUppercaseChars(name: String): ValidationResult[String] = {
    if ("[A-Z]".r.findFirstIn(name).isDefined) FieldNameContainsUpperCaseCharacters.invalidNec
    else name.validNec
  }

  private def validatePosition(position: Int): ValidationResult[Int] = {
    if (position < 0) FieldPositionIsNegative.invalidNec
    else if (position > columnLimit) FieldPositionMoreThanColLimit.invalidNec
    else position.validNec
  }

  sealed trait FieldDomainValidation extends AbstractDomainValidation with HasErrorMessage

  case object FieldNameIsNull extends FieldDomainValidation {
    def errorMessage: String = "'FieldNameIsNull: name parameter is null'"
  }
  case object FieldNameContainsInvalidCharacters extends FieldDomainValidation {
    def errorMessage: String = s"'FieldNameContainsInvalidCharacters: name parameter contains one or more of $invalidCharacters'"
  }
  case object FieldNameContainsUpperCaseCharacters extends FieldDomainValidation {
    def errorMessage: String = "'FieldNameContainsUpperCaseCharacters: name contains an upper-case character'"
  }
  case object FieldPositionIsNegative extends FieldDomainValidation {
    def errorMessage: String = "'FieldPositionIsNegative: position parameter is negative'"
  }
  case object FieldPositionMoreThanColLimit extends FieldDomainValidation {
    def errorMessage: String = s"'FieldPositionMoreThanColLimit: position parameter is greater than field limit $columnLimit'"
  }
}

object FieldValidator extends FieldValidator