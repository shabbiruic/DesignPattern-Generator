package Queries

import State.{BasicQuery, FieldSpecified}
import org.slf4j.LoggerFactory



/*
This is a parent class for all the EdgeNode Query
Parameters
  it takes List of field of specific trait as its parameter
 */
abstract class EdgeNodeQuery[State,F<:Fields.Field,Executable](fields:List[F]){

  val logger = LoggerFactory.getLogger(getClass)

  //this method is used to construct the query depending on the parameters user has specified while query construction
  def getQuery(): String = {
    logger.info("Got edge node query")
    "edges { node {"+ getFields + "}}"
  }


//  this method is to check whether query is constructed properly or not using phantom types
  def build(implicit state: State =:= Executable): EdgeNodeQuery[State,F,Executable] = {
    logger.info("Built edge node query")
    this
  }


//  method which provides the specified fields in query format while constructing query out of it
  def getFields: String = {
    logger.info("Got fields for edge node query")
    fields.foldLeft("")((x, y) => x.concat(" " + y.buildField() + " ,")).dropRight(1)
  }


//method which every child class should provide to set field for their object
  def setField(field: F): EdgeNodeQuery[State with FieldSpecified,F,Executable]



}