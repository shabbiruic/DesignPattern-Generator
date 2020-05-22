package Fields.CompositeFields

import Fields.FollowerField

//Wrapper to convert the query to a Specific type of field help in making composite query
trait CompositeFollowerField[State,Executable] {
  def buildAsFollowerField(implicit state: State =:= Executable):FollowerField}
