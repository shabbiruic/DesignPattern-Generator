package Fields.CompositeFields

import Fields.RepositoryField

//Wrapper to convert the query to a Specific type of field help in making composite query
trait CompositeRepositoryField[State,ExecutableState] {
  def buildAsRepositoryField(implicit state: State =:= ExecutableState):RepositoryField
}
