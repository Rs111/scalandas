package scalandas.sql.validation.schema

import cats.data.ValidatedNec
import cats.implicits._
import scalandas.sql.types.{Field, Schema}
import scalandas.sql.validation._

private[sql] sealed trait SchemaValidator extends AbstractValidator[SchemaValidatorInput, Schema] {

  override type E = SchemaDomainValidation with HasErrorMessage
  override type ValidationResult[A] = ValidatedNec[E, A]

  override def validate(input: SchemaValidatorInput): ValidationResult[Schema] = {
    validateFieldNameConsistency(input.fields)
      .map { case _ => new Schema(input.fields)}
  }

  private def validateFieldNameConsistency(fields: Array[Field]): ValidationResult[Array[Field]] = {
    val dupFieldNames =
      fields
        .map(field => field.getName)
        .groupBy(identity)
        .mapValues(v => v.length)
        .filter { case (_, v) => v > 1 }

    if (dupFieldNames.nonEmpty) SchemaFieldNamesDuplicates(dupFieldNames.keys.mkString(", ")).invalidNec
    else fields.validNec
  }

  sealed trait SchemaDomainValidation extends AbstractDomainValidation with HasErrorMessage

  case class SchemaFieldNamesDuplicates(s: String) extends SchemaDomainValidation {
    def errorMessage: String = s"'schema contains duplicate fields: $s'"
  }
}

object SchemaValidator extends SchemaValidator
