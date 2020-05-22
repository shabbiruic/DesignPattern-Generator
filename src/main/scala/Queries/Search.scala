package Queries

import Fields.SearchField
import Filters.objectType.ObjectType
import Filters.query.QueryFilter
import Filters.{objectType, query}
import Queries.Search.{Executable, SearchFilter}
import State.{BasicQuery, FieldSpecified}
import org.slf4j.LoggerFactory

/* Search Query class which extends the Query class for common Query methods and its signature
it also extends the BaseQuery which means it can act as a starting point for query construction

Fields:
it is the properties that you want to fetch for this object from GraphQL server.
Query can have as many field as it supports and it should inherit present Class queryField trait.
Query should have atleast one field else it can't be build.


Filters : this allow to constrain the fetched field which satisfy the mentioned filters condition only.
Query can have as many filters as it supports but at least it should have mandatory filters to make it build able.
It is a possible case that query doesn't have any filters specified
 */

case class Search[State](filters:List[SearchFilter],fields:List[SearchField]) extends Query[State,SearchFilter,SearchField,Executable](filters,fields) with BaseQuery[State,Executable]
{

  val logger = LoggerFactory.getLogger(getClass)

  // Method to construct query out of it
  def getQuery(): String = {
    logger.info("Got search query")
    getQuery("search")
  }


  // building query only when it is in its executable state
  override def build(implicit state: State =:= Executable): Search[State] = {
    logger.info("Built search query")
    this
  }


  // adding filters to query
  override def setFilter(filter: SearchFilter): Search[State] = {
    logger.info("Set filter for search query")
    Search[State](filter::filters,fields)
  }

  // adding mandatory filters and changing query state
  def setFilter(filter:objectType):Search[State with ObjectType]= {
    logger.info("Set filter for search query")
    Search[State with ObjectType](filter::filters,fields)
  }


  // adding mandatory filters and changing query state
  def setFilter(filter:query):Search[State with QueryFilter]= {
    logger.info("Set filter for search query")
    Search[State with QueryFilter](filter::filters,fields)
  }



  // adding fields to query and changing query state
  override def setField(field: SearchField): Search[State with FieldSpecified] = {
    logger.info("Set field for search query")
    Search[State with FieldSpecified](filters,field::fields)
  }


//Method which is used to make this query a baseQuery
  override def getBaseQuery(implicit state: State =:= Executable): RootQuery = {
    logger.info("Got base query for search")
    RootQuery(getQuery())
  }



}
// this Constructs the Query object without fields and filters specified with just a basic state.
object Search{
  def apply(): Search[BasicQuery] =
    new Search[BasicQuery](Nil,Nil)
  // trait to identify whether Filter is valid for this Query or not
  trait SearchFilter extends Filters.ParentFilter
  //State for this query to be buildable
  type Executable = BasicQuery with FieldSpecified with QueryFilter with ObjectType
}