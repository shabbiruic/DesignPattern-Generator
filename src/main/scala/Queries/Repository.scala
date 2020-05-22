package Queries

import Fields.CompositeFields.CompositeUserField
import Fields.GenericFields.GenericUserField
import Fields.{RepositoryField, UserField}
import Filters.name.Name
import Filters.owner.Owner
import Filters.{name, owner}
import Queries.Repository.{Executable, RepositoryFilter}
import State.{BasicQuery, FieldSpecified}
import org.slf4j.LoggerFactory
/* Repository Query class which extends the Query class for common Query methods and its signature
it also extends the compositeField class as it is acting as a composite field of some query whose composite--Fields
trait it extends (-- denotes the name of query whose CompositeField this query can be)
This also extends the BaseQuery which means it can act as a starting point for constructing a query

Field:
it is the properties that you want to fetch for this object from GraphQL server.
Query can have as many field as it supports and it should inherit present Class queryField trait.
Query should have atleast one field else it can't be build.


Filter : this allow to constrain the fetched field which satisfy the mentioned filters condition only.
Query can have as many filters as it supports but at least it should have mandatory filters to make it build able.
It is a possible case that query doesn't have any filters specified
 */

case class Repository[State](filters: List[RepositoryFilter], fields: List[RepositoryField]) extends Query[State, RepositoryFilter, RepositoryField, Executable](filters, fields) with CompositeUserField[State, Executable] with BaseQuery[State, Executable] {
  val logger = LoggerFactory.getLogger(getClass)

  // Method to construct query out of it
  def getQuery(): String = {
    logger.info("Got repository query")
    getQuery("repository")
  }


  //   making this query as a composite field
  override def buildAsUserField(implicit state: State =:= Executable): UserField = {
    logger.info("Build repository query as user field")
    new GenericUserField(getQuery())
  }


  // building query only when it is in its executable state
  override def build(implicit state: State =:= Executable): Repository[State] = {
    logger.info("Built repository query")
    this
  }

  // adding filters to query
  override def setFilter(filter: RepositoryFilter): Repository[State] = {
    logger.info("Set filter for repository query")
    Repository[State](filter :: filters, fields)
  }


//adding mandatory filters and changing query state
  def setFilter(filter: owner): Repository[State with Owner] = {
    logger.info("Set filter for repository query with owner")
    Repository[State with Owner](filter :: filters, fields)
  }


  // adding mandatory filters and changing query state
  def setFilter(filter: name): Repository[State with Name] = {
    logger.info("Set filter for repository query with name")
    Repository[State with Name](filter :: filters, fields)
  }


  // adding fields to query and changing query state
  override def setField(field: RepositoryField): Repository[State with FieldSpecified] = {
    logger.info("Set field for repository query")
    Repository[State with FieldSpecified](filters, field :: fields)
  }


// Constructing RootQuery Object(Only when it is in buildable state PHANTOM Type) which is used by GraphQl Client for making Query to Github Server
  override def getBaseQuery(implicit state: State =:= Executable): RootQuery = {
    logger.info("Got base query for repository")
    RootQuery(getQuery())
  }


}

// this Constructs the Query object without fields and filters specified with just a basic state.
object Repository {
  def apply(): Repository[BasicQuery] =
    new Repository[BasicQuery](Nil, Nil)
  // trait to identify whether Filter is valid for this Query or not
  trait RepositoryFilter extends Filters.ParentFilter

  //State for this query to be buildable
  type Executable = BasicQuery with FieldSpecified with Owner with Name
}