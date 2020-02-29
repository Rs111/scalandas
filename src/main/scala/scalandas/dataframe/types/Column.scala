package scalandas.dataframe.types

//https://blog.ssanj.net/posts/2019-08-18-using-validated-for-error-accumulation-in-scala-with-cats.html
import scalandas.dataframe.types.Types.DataType
import scalandas.dataframe.validation.ColumnValidator
import scalandas.dataframe.validation.ValidationOps._


class Column private[dataframe] (name: String, position: Int, dataType: DataType, nullable: Boolean) {

  def getName: String = name
  def getPosition: Int = position
  def getDataType: DataType = dataType
  def getTypeAsString: String = dataType.typeName

  //TODO add a nice print
}

object Column {

  implicit val className: String = "Column"

  def apply(name: String, position: Int, dataType: DataType, nullable: Boolean): Column = {
    ColumnValidator
      .validateColumn(name, position, dataType, nullable)
      .getValidationOutput
  }
}
