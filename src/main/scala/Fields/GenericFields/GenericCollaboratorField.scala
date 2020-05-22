package Fields.GenericFields

import Fields.CollaboratorField

/*
This are use to make a query as a field of Collaborator Query
 */
case class GenericCollaboratorField(completeField:String) extends CollaboratorField {
  override def buildField(): String =
    completeField
}

object GenericCollaboratorField{
  def apply(completeField: String): GenericCollaboratorField =
    new GenericCollaboratorField(completeField)
}