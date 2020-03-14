package scalandas.sql.types

import scalandas.sql.validation.schema.{SchemaValidator, SchemaValidatorInput}
import scalandas.sql.validation.ValidationOps._

class Schema private[sql](fields: Array[Field]) {

  def this() {
    this(Array[Field]())
  }

  def apply(i: Int): Field = fields(i)

  def add(field: Field): Schema = Schema.apply(this.fields :+ field)

  def toArray: Array[Field] = this.fields

  def length: Int = this.fields.length

  //TODO add a nice print
}

object Schema {

  implicit val className: String = "Schema"

  def apply(fields: Array[Field]): Schema = {
    SchemaValidator
      .validate(new SchemaValidatorInput(fields))
      .getValidationOutput
  }

  def apply(fields: Field*): Schema = apply(fields.toArray)

  def apply(): Schema = new Schema()

  def add(field: Field): Schema = Schema(Array(field))
}
