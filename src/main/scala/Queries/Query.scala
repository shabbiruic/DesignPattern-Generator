package Queries

import Fields.SearchField
import State.{FieldSpecified, QueryState}

/*
Parent Class that every Query except EdgeNode Query should inherit for basic functionality
parameters:
  List of Fields
  List of filters
 */
abstract class Query[State, A <: Filters.ParentFilter, F <: Fields.Field, ExecutableState](filters: List[A], fields: List[F]) {

  // building query only when it is in its executable state
  def build(implicit state: State =:= ExecutableState): Query[State, A, F, ExecutableState]

  // Method to construct query out of it
  def getQuery(queryName: String): String =
     queryName +  getFilters + "{" + getFields + "}"

//  Method which child class overrrides to provide name of query to be used for its construction
  def getQuery: String

  // adding filters to query
  def setFilter(filter: A): Query[State, A, F, ExecutableState]

  // adding fields to query and changing its state to FieldSpecified
  def setField(field: F): Query[State with FieldSpecified, A, F, ExecutableState]


//  Method to convert list of filters in the GraphQl query format
  def getFilters: String = {
    val filtersString: String = filters.foldLeft("")((x, y) => x.concat(" " + y.getFilter) + " ,").dropRight(1)
    filtersString match {
      case "" => ""
      case _ => "(" + filtersString + ")"
    }
  }
//  Method to construct the query fields in GraphQl query format
  def getFields: String =
    fields.foldLeft("")((x, y) => x.concat(" " + y.buildField() + " ,")).dropRight(1)

}
