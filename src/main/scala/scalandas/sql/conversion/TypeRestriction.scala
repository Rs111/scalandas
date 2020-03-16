package scalandas.sql.conversion

private[sql] sealed trait TypeRestriction[T]

object TypeRestriction {
  implicit object StringRestriction extends TypeRestriction[String]
  implicit object IntRestriction extends TypeRestriction[Int]
  implicit object LongRestriction extends TypeRestriction[Long]
  implicit object DoubleRestriction extends TypeRestriction[Double]
  implicit object AnyRestriction extends  TypeRestriction[Any]
}
