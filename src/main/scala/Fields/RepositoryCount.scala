package Fields


// It is a scalar filed means it is not a composite it has just one piece of information. it can act as a field
// in following queries i.e. which it extends
case class RepositoryCount(fieldName:String="repositoryCount") extends SearchField {

  override def buildField(): String =
    fieldName

}

object RepositoryCount{
  def apply()={
    new RepositoryCount()
  }
}