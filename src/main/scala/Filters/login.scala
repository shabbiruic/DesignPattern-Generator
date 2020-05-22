package Filters

import Queries.Collaborator.CollaboratorFilter
import Queries.User.UserFilter

// This is a filter class which can act as a filter for all the type of filter traits it extends
case class login(value:String,key:String="login") extends Filter[String,String](value,key) with CollaboratorFilter with UserFilter

object login{
  //Construct the object of this filter with default key
  def apply(value: String): login =
    new login(value)

  //  this trait is used in changing the query state when ever this filter is acting as a mandatory filter for a query
  trait Login
}