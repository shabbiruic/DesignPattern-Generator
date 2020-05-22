package Fields


// It is a scalar field i.e. it is not a composite it has just one piece of information it can act as a field
// in following queries i.e. which it extends
case class Company(fieldName:String="company") extends Field with UserField with FollowersEdgeNodeField with CollaboratorEdgeNodeField {
  override def buildField(): String =
    fieldName
}

object Company{
  def apply():Company=
  new Company()
}