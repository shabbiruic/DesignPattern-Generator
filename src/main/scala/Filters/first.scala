package Filters

import Queries.Collaborator.CollaboratorFilter
import Queries.Followers.FollowersFilter
import Queries.Repositories.RepositoriesFilter
import Queries.Search.SearchFilter
import Queries.User.UserFilter


// This is a filter class which can act as a filter for all the type of filter traits it extends
case class first(value: Int,key:String="first") extends Filter[Int,String](value,key) with UserFilter with CollaboratorFilter with RepositoriesFilter with SearchFilter with FollowersFilter

object first{

  //Construct the object of this filter with default key
  def apply(value: Int): first =
    new first(value)

  //  this trait is used in changing the query state when ever this filter is acting as a mandatory filter for a query
  trait First
}