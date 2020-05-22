package Filters


// This is a filter class which can act as a filter for all the type of filter traits it extends
case class states(value: String, key:String="states") extends Filter[String,String](value,key)

object states{
  //Construct the object of this filter with default key
  def apply(value: String): states = new states(value)

  //  this trait is used in changing the query state when ever this filter is acting as a mandatory filter for a query
  trait States
}

