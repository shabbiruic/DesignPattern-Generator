import java.io.{File, FileWriter}

import Converters.FilterConverter
import Fields.{Company, Email, Login, Name, TotalCount}
import Filters.{first, login, name, owner}
import Queries.{Collaborator, CollaboratorEdgeNode, FollowerEdgeNode, Followers, Repositories, Repository, RootQuery, User}
import QueryExecutor.config
import com.typesafe.config.{Config, ConfigFactory}
import org.junit.Test
import junit.framework.TestCase
import org.junit.Assert._
import org.junit.{After, Assert, Before, Test}

class TestSuite {

  val config : Config = ConfigFactory.parseFile(new File("src/main/resources/application.conf"))

  @Test
  def validateToken : Unit  = {
    val user = User().setFilter(login("Mohammed-siddiq")).setField(Followers().setFilter(first(100)).setField(TotalCount()).setField(FollowerEdgeNode().setField(Name()).setField(Email()).setField(Company()).setField(Login()).buildAsFollowerField).buildAsUserField).getBaseQuery
    val client = GraphQLClient().setAuthorization("").fold(l => l.message, r => r.setQuery(user).fold(l => l.message, r => r.build))
    assertEquals(config.getString("error-message.invalid-token"),client)
  }

  @Test
  def validateUserQuery: Unit = {
    val user = User().setFilter(login("Mohammed-siddiq")).setField(Followers().setFilter(first(10)).setField(TotalCount()).setField(FollowerEdgeNode().setField(Login()).buildAsFollowerField).buildAsUserField).getBaseQuery
    assertEquals(config.getString("query-output.query1"),user.toString)
  }

  @Test
  def validateRepoQuery : Unit = {
    val repo = Repository().setFilter(owner("Mohammed-siddiq")).setFilter(name("Cloud-DataCenter-Simulations")).setField(Collaborator().setField(CollaboratorEdgeNode().setField(Name()).buildAsCollaboratorField).buildAsRepositoryField).getBaseQuery
    assertEquals(config.getString("query-output.query2"),repo.toString)
  }

  @Test
  def validateResult1 : Unit = {
    val user = User().setFilter(login("Mohammed-siddiq")).setField(Followers().setFilter(first(100)).setField(TotalCount()).setField(FollowerEdgeNode().setField(Name()).setField(Email()).setField(Company()).setField(Login()).buildAsFollowerField).buildAsUserField).getBaseQuery
    val client = GraphQLClient().setAuthorization(config.getString("github-Personal-Access-Token")).fold(l => l.message, r => r.setQuery(user).fold(l => l.message, r => r.build))
    assertEquals(config.getString("result-output.result1"),client.toString)
  }

  @Test
  def validateResult2 : Unit = {
    val user = User().setFilter(login("Mohammed-siddiq")).setField(Repositories().setField(TotalCount()).buildAsUserField).getBaseQuery
    val client = GraphQLClient().setAuthorization(config.getString("github-Personal-Access-Token")).fold(l => l.message, r => r.setQuery(user).fold(l => l.message, r => r.build))
    assertEquals(config.getString("result-output.result2"),client.toString)

  }

  @Test
  def validateFilter : Unit = {
    val user = User().setFilter(login("rahulromilkeswani")).setField(Repositories().setField(TotalCount()).buildAsUserField).getBaseQuery
    val client = GraphQLClient().setAuthorization(config.getString("github-Personal-Access-Token")).fold(l => l.message, r => r.setQuery(user).fold(l => l.message, r => r.build))
    val filtered_client = FilterConverter().filtercon(client)
    val filter_result = FilterConverter().filter(filtered_client.user.repositories.totalCount)
    assertEquals(config.getString("filter-output.output"), filter_result)
  }
}