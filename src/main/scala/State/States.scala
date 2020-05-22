package State



// these are the traits that are used to identify the state of  a query while constructing it.
sealed trait QueryState

// Every query starts with this state.
trait BasicQuery extends QueryState

// query is set to this state when alteast one field is set to it.
trait FieldSpecified extends QueryState

// this state is only used in query where only one filter mandatory for its execution.
trait FilterSpecified extends QueryState
