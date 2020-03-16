package scalandas.sql.types

//https://blog.ssanj.net/posts/2019-08-18-using-validated-for-error-accumulation-in-scala-with-cats.html
import scalandas.sql.types.Types.DataType
import scalandas.sql.validation.field.{FieldValidator, FieldValidatorInput}
import scalandas.sql.validation.ValidationOps._


class Field private[sql](name: String, position: Int, dataType: DataType, nullable: Boolean) {

  def getName: String = name
  def getPosition: Int = position
  def getDataType: DataType = dataType
  def getTypeAsString: String = dataType.typeName

  override def toString: String = s"Field($name, $position, $dataType, $nullable)"
}

object Field {

  implicit val className: String = "Field"

  def apply(name: String, position: Int, dataType: DataType, nullable: Boolean): Field = {
    FieldValidator
      .validate(new FieldValidatorInput(name, position, dataType, nullable))
      .getValidationOutput
  }
}
