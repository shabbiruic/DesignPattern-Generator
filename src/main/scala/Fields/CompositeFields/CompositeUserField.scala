package Fields.CompositeFields

import Fields.UserField

//Wrapper to convert the query to a Specific type of field help in making composite query
trait CompositeUserField[State,ExecutableState] {
  def buildAsUserField(implicit state: State =:= ExecutableState):UserField
}
