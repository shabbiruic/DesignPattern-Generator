package Queries

import Fields.CompositeFields.CompositeFollowerField
import Fields.GenericFields.GenericFollowerField
import Fields.{CollaboratorField, FollowerField, FollowersEdgeNodeField, RepositoryField}
import Queries.FollowerEdgeNode.{Executable, FollowerEdgeNodeFilter}
import State.{BasicQuery, FieldSpecified}
import org.slf4j.LoggerFactory


/* FollowerEdgeNode Query class which extends the EdgeNodeQuery class for common Query methods and its signature
it also extends the compositeField class as it is acting as a composite field of some query whose composite--Fields
trait it extends (-- denotes the name of query whose CompositeField this query can be)

Field:
it is the properties that you want to fetch for this object from GraphQL server.
Query can have as many field as it supports and it should inherit present Class queryField trait.
Query should have atleast one field else it can't be build.
 */
case class FollowerEdgeNode[State](fields:List[FollowersEdgeNodeField]) extends EdgeNodeQuery[State,FollowersEdgeNodeField,Executable](fields) with CompositeFollowerField[State,Executable]
{
  override val logger = LoggerFactory.getLogger(getClass)

  //  making this query as a composite field
  override def buildAsFollowerField(implicit state: State =:= Executable): FollowerField = {
    logger.info("Built follower edge node query as follower field")
    new GenericFollowerField(getQuery())
  }

  // adding fields to query
  def setField(field: FollowersEdgeNodeField): FollowerEdgeNode[State with FieldSpecified] = {
    logger.info("Set field for follower edge node")
    FollowerEdgeNode[State with FieldSpecified](field::fields)
  }

}

object FollowerEdgeNode{
  def apply(): FollowerEdgeNode[BasicQuery] =
    new FollowerEdgeNode[BasicQuery](Nil)
  // this Constructs the Query object without fields specified with just a basic state.
  trait FollowerEdgeNodeFilter extends Filters.ParentFilter
  //State for this query to be buildable
  type Executable = BasicQuery with FieldSpecified
}