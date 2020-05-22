package Queries

import Fields.CompositeFields.CompositeCollaboratorField
import Fields.GenericFields.GenericCollaboratorField
import Fields.{CollaboratorEdgeNodeField, CollaboratorField}
import Queries.CollaboratorEdgeNode.Executable
import State.{BasicQuery, FieldSpecified}
import org.slf4j.LoggerFactory

/* CollaboratorEdgeNode Query class which extends the EdgeNodeQuery class for common Query methods and its signature
it also extends the compositeField class as it is acting as a composite field of some query whose composite--Fields
trait it extends (-- denotes the name of query whose CompositeField this query can be)

Field:
it is the properties that you want to fetch for this object from GraphQL server.
Query can have as many field as it supports and it should inherit present Class queryField trait.
Query should have atleast one field else it can't be build.
 */
case class  CollaboratorEdgeNode[State](fields:List[CollaboratorEdgeNodeField])extends EdgeNodeQuery[State,CollaboratorEdgeNodeField,Executable] (fields) with CompositeCollaboratorField[State,Executable] {

  override val logger = LoggerFactory.getLogger(getClass)

//  making this query as a composite field
  override def buildAsCollaboratorField(implicit state: State =:= Executable): CollaboratorField = {
    logger.info("Built collaborator edge node as collaborator field")
    new GenericCollaboratorField(getQuery())
  }


  // adding fields to query
  override def setField(field: CollaboratorEdgeNodeField): CollaboratorEdgeNode[State with FieldSpecified] = {
    logger.info("Set fields for collaborator edge node query")
    CollaboratorEdgeNode[State with FieldSpecified](field::fields)
  }


}
// this Constructs the Query object without fields specified with just a basic state.
object CollaboratorEdgeNode{

  //State for this query to be buildable
  type Executable = BasicQuery with FieldSpecified
  def apply(): CollaboratorEdgeNode[BasicQuery] =
    new CollaboratorEdgeNode[BasicQuery](Nil)
}