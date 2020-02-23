package scalandas.dataframe.types.column

//https://blog.ssanj.net/posts/2019-08-18-using-validated-for-error-accumulation-in-scala-with-cats.html
import cats.data.Chain
import cats.data.Validated.{Invalid, Valid}
import scalandas.dataframe.types.Types.DataType

class Column private[column] (name: String, position: Int, dataType: DataType, nullable: Boolean) {

  def getName: String = name
  def getPosition: Int = position
  def getDataType: DataType = dataType
  def getTypeAsString: String = dataType.typeName
}

object Column {

  def apply(name: String, position: Int, dataType: DataType, nullable: Boolean): Column = {
    ColumnValidatorNec.validateColumn(name, position, dataType, nullable) match {
      case Valid(column) => column
      case Invalid(err) =>
        throw new IllegalArgumentException(
          s"Column creation failed with errors:" +
            s"\n\t${err.toChain.toList.map(_.errorMessage).mkString("\n\t")}"
        )
    }
  }
}
