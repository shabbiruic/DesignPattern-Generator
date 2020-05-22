package Fields.GenericFields

import Fields.FollowerField

//This are use to make a query as a field of Follower Query
case class GenericFollowerField(completeField:String) extends FollowerField {
  override def buildField(): String =
    completeField
}

object GenericFollowerField {

}
