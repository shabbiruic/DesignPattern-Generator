import java.io.{File, FileWriter}
import java.util

import Converters.{FC, FilterConverter}
import Fields.{Company, Email, Login, Name, RepositoryCount, TotalCount}
import Filters.{first, login, name, objectType, owner, query}
import Queries.{Collaborator, CollaboratorEdgeNode, FollowerEdgeNode, Followers, Repositories, Repository, Search, User}
import com.typesafe.config.{Config, ConfigFactory}

object QueryExecutor {

  val config : Config = ConfigFactory.parseFile(new File("src/main/resources/application.conf"))

  def main(args: Array[String]): Unit = {

   
    val user = User().setFilter(login("rahulromilkeswani")).setField(Repositories().setField(TotalCount()).buildAsUserField).getBaseQuery
    println("GraphQL Query constructed :- " + user.getQuery())

    /** Build your query by passing your Github personal access token and the variable used to store the OO query below */
    val client = GraphQLClient().setAuthorization(config.getString("github-Personal-Access-Token")).fold(l => l.message, r => r.setQuery(user).fold(l => l.message, r => r.build))
    println("Response received :- " + client)

    GraphQLClient().writeIntoFile(client, config.getString("client_result_file_path"))

    if (client != config.getString("error-message.invalid-token") && client != config.getString("error-message.invalid-query" )) {
      val filtered_client = FilterConverter().filtercon(client)

      /**  Filter the result of the GraphQL query based on the requirements and pass below in the filter function  **/
      val filter_result = FilterConverter().filter(filtered_client.user.repositories.totalCount)

      GraphQLClient().writeIntoFile(filter_result, config.getString("filtered_result_file_path"))
    }

  }
}