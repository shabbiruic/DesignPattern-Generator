package Filters

import Queries.Collaborator.CollaboratorFilter
import Queries.Repositories.RepositoriesFilter
import Queries.Search.SearchFilter
import Queries.User.UserFilter

// This is a filter class which can act as a filter for all the type of filter traits it extends
case class query(value: String,key:String="query") extends Filter[String,String](value,key) with SearchFilter

object query
{
  //Construct the object of this filter with default key
  def apply(value: String): query = new query(value)

  //  this trait is used in changing the query state when ever this filter is acting as a mandatory filter for a query
  trait QueryFilter
}