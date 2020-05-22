package Queries

import Fields.CompositeFields.CompositeRepositoryField
import Fields.GenericFields.GenericRepositoryField
import Fields.{CollaboratorField, RepositoryField, UserField}
import Queries.Collaborator.{CollaboratorFilter, Executable}
import State.{BasicQuery, FieldSpecified}
import org.slf4j.LoggerFactory

/* Collaborator Query class which extends the Query class for common Query methods and its signature
it also extends the compositeField class as it is acting as a composite field of some query whose composite--Fields
trait it extends (-- denotes the name of query whose CompositeField this query can be)

Field:
it is the properties that you want to fetch for this object from GraphQL server.
Query can have as many field as it supports and it should inherit present Class queryField trait.
Query should have atleast one field else it can't be build.


Filter : this allow to constrait the fetched field which satisfy the mentioned filters condition only.
Query can have as many filters as it supports but at least it should have mandatory filters to make it build able.
It is a possible case that query doesn't have any filters specified
 */

case class Collaborator[State](filters:List[CollaboratorFilter],fields:List[CollaboratorField]) extends Query[State,CollaboratorFilter,CollaboratorField,Executable](filters,fields) with CompositeRepositoryField[State,Executable]
{
  val logger = LoggerFactory.getLogger(getClass)

  // Providing the name that parent Query Class should use while constructing query for this
  def getQuery(): String = {
    logger.info("Got collaborator query")
    getQuery("collaborators")
  }

// making this query as a composite field
  override def buildAsRepositoryField(implicit state: State =:= Executable): RepositoryField = {
    logger.info("Built collaborator query as repository field")
    new GenericRepositoryField(getQuery())
  }


// building query only when it is in its executable state
  override def build(implicit state: State =:= Executable): Collaborator[State] = {
    logger.info("Built collaborator query")
    this
  }


// adding filters to query
  override def setFilter(filter: CollaboratorFilter): Collaborator[State ] = {
    logger.info("Set filter for collaborator query")
    Collaborator[State](filter::filters,fields)
  }



  // adding fields to query
  def setField(field: CollaboratorField): Collaborator[State with FieldSpecified] = {
    logger.info("Set field for collaborator query")
    Collaborator[State with FieldSpecified](filters,field::fields)
  }

}


// this Constructs the Query object without fields and filters specified with just a basic state.
object Collaborator{
  def apply(): Collaborator[BasicQuery] =
    new Collaborator[BasicQuery](Nil,Nil)

  // trait to identify whether Filter is valid for this Query or not
  trait CollaboratorFilter extends Filters.ParentFilter

  //State for this query to be buildable
  type Executable = BasicQuery with FieldSpecified
}