package Queries

import Fields.CompositeFields.CompositeUserField
import Fields.GenericFields.GenericUserField
import Fields.{FollowerField, RepositoriesField, RepositoryField, UserField}
import Filters.{first, last}
import Queries.Repositories.{Executable, RepositoriesFilter}
import State.{BasicQuery, FieldSpecified}
import org.slf4j.LoggerFactory

/* Repositories Query class which extends the Query class for common Query methods and its signature
it also extends the compositeField class as it is acting as a composite field of some query whose composite--Fields
trait it extends (-- denotes the name of query whose CompositeField this query can be)

Field:
it is the properties that you want to fetch for this object from GraphQL server.
Query can have as many field as it supports and it should inherit present Class queryField trait.
Query should have atleast one field else it can't be build.


Filter : this allow to constrain the fetched field which satisfy the mentioned filters condition only.
Query can have as many filters as it supports but at least it should have mandatory filters to make it build able.
It is a possible case that query doesn't have any filters specified
 */
case class Repositories[State](filters: List[RepositoriesFilter], fields: List[RepositoriesField]) extends Query[State, RepositoriesFilter, RepositoriesField, Executable](filters, fields) with CompositeUserField[State, Executable] {
  val logger = LoggerFactory.getLogger(getClass)

  // Method to construct query out of it
  def getQuery(): String = {
    logger.info("Got repositories query")
    getQuery("repositories")
  }

  //   making this query as a composite field
  override def buildAsUserField(implicit state: State =:= Executable): UserField = {
    logger.info("Built repositories query as user field")
    new GenericUserField(getQuery())
  }

  // building query only when it is in its executable state
  override def build(implicit state: State =:= Executable): Repositories[State] = {
    logger.info("Built repositories query")
    this
  }


  // adding filters to query
  override def setFilter(filter: RepositoriesFilter): Repositories[State] =
    {
      logger.info("Set filter for repositories query")
      Repositories[State](filter :: filters, fields)
    }

  // adding fields to query
  override def setField(field: RepositoriesField): Repositories[State with FieldSpecified] = {
    logger.info("Set field for repositories query")
    Repositories[State with FieldSpecified](filters, field :: fields)
  }



}

// this Constructs the Query object without fields and filters specified with just a basic state.
object Repositories {
  def apply(): Repositories[BasicQuery] =
    new Repositories[BasicQuery](Nil, Nil)

  // trait to identify whether Filter is valid for this Query or not
  trait RepositoriesFilter extends Filters.ParentFilter


  //State for this query to be buildable
  type Executable = BasicQuery with FieldSpecified
}