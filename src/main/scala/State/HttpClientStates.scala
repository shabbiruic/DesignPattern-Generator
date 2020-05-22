package State

// traits are used to oidentify the present state of out Graph QL client before hitting the server

// Client object starting state
sealed trait PlainClient

//State when token is provided
trait Authorized extends PlainClient

// State when baseQuery is provided
trait WithQuery extends PlainClient



// this is final state in which client object should be before executing the query
object HttpClientStates {
  type complete = PlainClient with Authorized with WithQuery
}
