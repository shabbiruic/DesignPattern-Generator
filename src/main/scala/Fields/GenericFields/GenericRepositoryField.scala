package Fields.GenericFields

import Fields.RepositoryField

//This are use to make a query as a field of Repository Query
case class GenericRepositoryField(completeField:String) extends RepositoryField {
  override def buildField(): String =
    completeField
}

object GenericRepositoryFieldField{
  def apply(completeField: String): GenericRepositoryField =
    new GenericRepositoryField(completeField)
}