package scalandas.dataframe.validation

import cats.data.ValidatedNec
import cats.implicits._
import scalandas.dataframe.types.{Column, Schema}

private[dataframe] sealed trait SchemaValidatorNec {

  type ValidationResult[A] = ValidatedNec[SchemaDomainValidation, A]

  def validateSchema(columns: Array[Column]): ValidationResult[Schema] = {
    validateColumnNameConsistency(columns)
      .map { case _ => new Schema(columns)}
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

  sealed trait SchemaDomainValidation extends HasErrorMessage

  case class SchemaColumnNamesDuplicates(s: String) extends SchemaDomainValidation {
    def errorMessage: String = s"'schema contains duplicate columns: $s'"
  }
}

object SchemaValidatorNec extends SchemaValidatorNec
