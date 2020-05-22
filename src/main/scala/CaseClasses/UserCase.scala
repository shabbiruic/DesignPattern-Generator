package CaseClasses

case class UserCase(repositories: RepositoriesCase, company: Option[String], email: Option[String],
                    followersCase: FollowersCase, followingCase: FollowingCase, login: Option[String],
                    name: Option[String])
