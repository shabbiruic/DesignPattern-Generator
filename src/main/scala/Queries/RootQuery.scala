package Queries


/*
It object are only eligible to be passed a Query to GraphQL Client
this is Basically use to identify which query can act as a starting query
 */
trait BaseQuery[State,ExecutableState]{

  // gives RootQuery object only when complete query which all it composite queries is in proper format.
 def getBaseQuery(implicit state:State=:=ExecutableState):RootQuery
}

case class RootQuery(query:String) {

//  this method is Used by GraphQL Client to construct the Query which is passed to Github server
  def getQuery():String=
    "{"+query+"}"
}

object RootQuery{
  def apply(query: String): RootQuery =
    new RootQuery(query)
}

