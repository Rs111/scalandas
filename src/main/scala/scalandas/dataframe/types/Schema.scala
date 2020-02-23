package scalandas.dataframe.types

import cats.data._
import cats.data.Validated._
import cats.implicits._
import scalandas.dataframe.types.column.Column

import scala.util.{Failure, Success, Try}


class Schema private (columns: Array[Column]) {

  def this() {
    this(Array[Column]())
  }

  def add(column: Column): Schema = new Schema(this.columns :+ column)

  def toArray: Array[Column] = this.columns
}

object Schema {
  def apply(columns: Array[Column]): Schema = Schema(columns)
  def apply(columns: Column*): Schema = Schema(columns.toArray)

  def add(column: Column): Schema = Schema(Array(column))


  //TODO enforce that columns can't have same position or names
  //TODO use more try logic with private methods
//  private def validateInput(columns: Array[Column]): Validated[DomainValidation, Array[Column]] = {
//    for {
//
//    }
//  }
//
//  def validateNonNegativeColumnPositions(columns: Array[Column]): Validated[DomainValidation, Array[Column]] = {
//    columns.filter(column => column.)//.toValidated
//  }

  sealed trait DomainValidation {
    def errorMessage: String = "fake error msg" //TODO make real error message
  }

  case object SchemaColumnPositionsDoNotStartAt0 extends DomainValidation
  case object SchemaColumnPositionsNotSequential extends DomainValidation
  case object SchemaColumnPositionsDuplicates extends DomainValidation
  case object SchemaColumnNamesDuplicates extends DomainValidation

}
