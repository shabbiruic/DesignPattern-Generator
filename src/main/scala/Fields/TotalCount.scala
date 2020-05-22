package Fields

case class TotalCount(fieldName:String="totalCount") extends Field with RepositoryField with FollowerField with RepositoriesField {

  override def buildField(): String =
    fieldName
}
