package Fields.CompositeFields

import Fields.CollaboratorField

//Wrapper to convert the query to a Specific type of field help in making composite query
trait CompositeCollaboratorField[State,Executable] {
  def buildAsCollaboratorField(implicit state: State =:= Executable):CollaboratorField}
