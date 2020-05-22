package Filters

import Queries.Collaborator.CollaboratorFilter
import Queries.Repository.RepositoryFilter

// This is a filter class which can act as a filter for all the type of filter traits it extends
case class name(value: String, key: String = "name") extends Filter[String,String](value,key) with RepositoryFilter with CollaboratorFilter

object  name{
  //Construct the object of this filter with default key
  def apply(value: String): name = new name(value)

  //  this trait is used in changing the query state when ever this filter is acting as a mandatory filter for a query
  trait Name
}