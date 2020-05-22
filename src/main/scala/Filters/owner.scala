package Filters

import Queries.Collaborator.CollaboratorFilter
import Queries.Repository.RepositoryFilter

// This is a filter class which can act as a filter for all the type of filter traits it extends
case class owner(value: String, key: String = "owner") extends Filter[String,String](value,key) with RepositoryFilter with CollaboratorFilter

object owner{
  //Construct the object of this filter with default key
  def apply(value: String): owner = new owner(value)

  //  this trait is used in changing the query state when ever this filter is acting as a mandatory filter for a query
  trait Owner
}
