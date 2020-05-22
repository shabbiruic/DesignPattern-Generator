package Filters

import Queries.Collaborator.CollaboratorFilter
import Queries.Repositories.RepositoriesFilter
import Queries.Search.SearchFilter
import Queries.User.UserFilter


// This is a filter class which can act as a filter for all the type of filter traits it extends
case class before(value: String,key:String="before") extends Filter[String,String](value,key) with SearchFilter

object before{

  //Construct the object of this filter with default key
  def apply(value: String): before = new before(value)
  //  this trait is used in changing the query state when ever this filter is acting as a mandatory filter for a query
  trait Before
}