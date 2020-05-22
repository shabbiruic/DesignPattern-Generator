package Queries

import Fields.CompositeFields.{CompositeRepositoryField, CompositeUserField}
import Fields.GenericFields.{GenericRepositoryField, GenericUserField}
import Fields.{FollowerField, RepositoryField, UserField}
import Filters.{first, last}
import Queries.Followers.{Executable, FilterSpecified, FollowersFilter}
import State.{BasicQuery, FieldSpecified}
import org.slf4j.LoggerFactory


/* Followers Query class which extends the Query class for common Query methods and its signature
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
case class Followers[State](filters:List[FollowersFilter],fields:List[FollowerField]) extends Query[State,FollowersFilter,FollowerField,Executable](filters,fields) with CompositeRepositoryField[State,Executable] with CompositeUserField[State,Executable]
{
  val logger = LoggerFactory.getLogger(getClass)

  // Method to construct query out of this object
  def getQuery(): String = {
    logger.info("Got followers query")
    getQuery("followers")
  }


  //  making this query as a composite field
  override def buildAsRepositoryField(implicit state: State =:= Executable): RepositoryField = {
    logger.info("Built follower query as repository field")
    new GenericRepositoryField(getQuery())
  }


  // building query only when it is in its executable state
  override def build(implicit state: State =:= Executable): Followers[State] = {
    logger.info("Built follower query")
    this
  }


  // adding filters to query
  override def setFilter(filter: FollowersFilter): Followers[State] = {
    logger.info("Set filter for follower query")
    Followers[State](filter::filters,fields)
  }

// adding mandatory filters and changing query state
  def setFilter(filter: first): Followers[State with FilterSpecified] = {
    logger.info("Set filter for follower query")
    Followers[State with FilterSpecified](filter::filters,fields)
  }


  // adding mandatory filters and changing query state
  def setFilter(filter: last): Followers[State with FilterSpecified] = {
    logger.info("Set filter for follower query")
    Followers[State with FilterSpecified](filter::filters,fields)
  }


  // adding fields to query
  def setField(field: FollowerField): Followers[State with FieldSpecified] = {
    logger.info("Set field for follower query")
    Followers[State with FieldSpecified](filters,field::fields)
  }


//   making this query as a composite field
  override def buildAsUserField(implicit state: State =:= Executable): UserField = {
    logger.info("Built follower query as user field")
    new GenericUserField(getQuery())
  }


}
// this Constructs the Query object without fields and filters specified with just a basic state.
object Followers{
  def apply(): Followers[BasicQuery] =
    new Followers[BasicQuery](Nil,Nil)
  // trait to identify whether Filter is valid for this Query or not
  trait FollowersFilter extends Filters.ParentFilter
  trait FilterSpecified

  //State for this query to be buildable
  type Executable = BasicQuery with FieldSpecified with FilterSpecified
}