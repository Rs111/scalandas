package scalandas.dataframe.validation

import cats.data.ValidatedNec
import cats.implicits._
import scalandas.dataframe.types.{Column, Schema}

private[dataframe] sealed trait SchemaValidator extends AbstractValidator {

  override type ValidationResult[A] = ValidatedNec[SchemaDomainValidation, A]

  override def validate[I <: SchemaValidatorInput](input: I): ValidationResult[Schema] = {
    validateColumnNameConsistency(input.columns)
      .map { case _ => new Schema(input.columns)}
  }

  private def validateColumnNameConsistency(columns: Array[Column]): ValidationResult[Array[Column]] = {
    val dupColumnNames =
      columns
        .map(column => column.getName)
        .groupBy(identity)
        .mapValues(v => v.length)
        .filter { case (_, v) => v > 1 }

    if (dupColumnNames.nonEmpty) SchemaColumnNamesDuplicates(dupColumnNames.keys.mkString(", ")).invalidNec
    else columns.validNec
  }

  sealed trait SchemaDomainValidation extends AbstractDomainValidation with HasErrorMessage

  case class SchemaColumnNamesDuplicates(s: String) extends SchemaDomainValidation {
    def errorMessage: String = s"'schema contains duplicate columns: $s'"
  }
}

object SchemaValidator extends SchemaValidator
