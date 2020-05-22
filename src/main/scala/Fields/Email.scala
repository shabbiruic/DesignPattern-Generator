package Fields

// It is a scalar field means it is not a composite it has just one piece of information it can act as a field
// in following queries i.e. which it extends
case class Email(fieldName:String="email") extends FollowersEdgeNodeField with CollaboratorEdgeNodeField   {

  override def buildField(): String =
    fieldName

}


