package Filters

import Queries.Search.SearchFilter


// This is a filter class which can act as a filter for all the type of filter traits it extends
case class objectType(value:String,key:String="type") extends Filter[String,String](value,key) with SearchFilter{

  override def getFilter(): String =
    key+" : " + value
}

object objectType{

  def apply(value: String): objectType =
    new objectType(value)
  trait ObjectType
}

