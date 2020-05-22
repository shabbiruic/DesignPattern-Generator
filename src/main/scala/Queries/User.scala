package Queries

import Fields.{SearchField, UserField}
import Filters.login.Login
import Filters.{login, name}
import Queries.User.{UserFilter, executable}
import State.{BasicQuery, FieldSpecified}
import org.slf4j.LoggerFactory

/* User Query class which extends the Query class for common Query methods and its signature
it also extends the BaseQuery which means it can act as a starting point for query construction

Fields:
it is the properties that you want to fetch for this object from GraphQL server.
Query can have as many field as it supports and it should inherit present Class queryField trait.
Query should have atleast one field else it can't be build.


Filters : this allow to constrain the fetched field which satisfy the mentioned filters condition only.
Query can have as many filters as it supports but at least it should have mandatory filters to make it build able.
It is a possible case that query doesn't have any filters specified
 */
case class User[State](filters: List[UserFilter], fields: List[UserField]) extends Query[State, UserFilter, UserField, executable](filters, fields) with BaseQuery[State, executable] {
  val logger = LoggerFactory.getLogger(getClass)

  // Method to construct query out of it
  override def getQuery(): String = {
    logger.info("Got user query")
    getQuery("user")
  }

  // building query only when it is in its executable state
  override def build(implicit state: State =:= executable): User[State] = {
    logger.info("Built user query")
    new User[State](filters, fields)
  }

  // adding filters to query
  override def setFilter(filter: UserFilter): User[State] = {
    logger.info("Filter for user query set")
    User[State](filter :: filters, fields)
  }


  // adding mandatory filters and changing query state
  def setFilter(filter: login): User[State with Login] = {
    logger.info("Filter set for user query with login")
    User[State with Login](filter :: filters, fields)
  }


  // adding fields to query and changing query state
  override def setField(field: UserField): User[State with FieldSpecified] = {
    logger.info("Field set for user query")
    User[State with FieldSpecified](filters, field :: fields)
  }


//  Method which is used to make this query a baseQuery
  override def getBaseQuery(implicit state: State =:= executable): RootQuery = {
    logger.info("Got base user query")
    RootQuery(getQuery())
  }


}

// this Constructs the Query object without fields and filters specified with just a basic state.
object User {
  def apply() =
    new User[BasicQuery](Nil, Nil)

  // trait to identify whether Filter is valid for this Query or not
  trait UserFilter extends Filters.ParentFilter

  //State for this query to be buildable
  type executable = BasicQuery with FieldSpecified with Login
}