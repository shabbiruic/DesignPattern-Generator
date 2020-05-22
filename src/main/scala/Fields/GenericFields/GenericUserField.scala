package Fields.GenericFields

import Fields.UserField

//This is use to make a query as a field of User Query
case class GenericUserField(completeField:String) extends UserField {
  override def buildField(): String =
    completeField
}

object GenericUserField{
  def apply(completeField: String): GenericUserField =
    new GenericUserField(completeField)
}