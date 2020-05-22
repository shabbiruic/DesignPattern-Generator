import java.io.{File, FileWriter, InputStream, InputStreamReader, Reader}

import Queries.RootQuery
import QueryExecutor.config
import State.HttpClientStates.complete
import State.{Authorized, PlainClient, WithQuery}
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.{ContentType, StringEntity}
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.slf4j.LoggerFactory

case class ErrorMessage(message: String)

/**
Class act as a client which queries the github server with the query and token that is passed to it only when it is in its
complete state i.e. both authorization and query are passed to it

baseURL is set to default as it will be always be the github GraphQL server end point in our Use case
 */

case class GraphQLClient[State](token: String, query: RootQuery, baseUrl: String = "https://api.github.com/graphql") {

  val logger = LoggerFactory.getLogger(getClass)

//  Set the authorization and change the state of object
  def setAuthorization(token: String): Either[ErrorMessage, GraphQLClient[State with Authorized]] = {
    if (token != "") {
      logger.info("Token authorized with GraphQL client.")
      Right(new GraphQLClient[State with Authorized](token, query))
    }
    else {
      logger.info("Token is invalid")
      Left(new ErrorMessage(config.getString("error-message.invalid-token")))
    }
  }


//  To set the query and change the state of object
  def setQuery(query: RootQuery): Either[ErrorMessage, GraphQLClient[State with WithQuery]] = {
    if (RootQuery != null || RootQuery != "") {
      logger.info("Query set to - " + query)
      Right(new GraphQLClient[State with WithQuery](token, query))
    } else {
      logger.info("Query is invalid")
      Left(new ErrorMessage(config.getString("error-message.invalid-token")))
    }
  }
// it Fires the query to github only when object is in complete state(it is consider to be the only valid state for execution)
  def build(implicit state: State =:= complete): String = {
    val httpclient = HttpClients.createDefault
    val httpUriRequest = new HttpPost(baseUrl)

    // setting the authorization token
    httpUriRequest.addHeader("Authorization", "bearer " + token)

    //Setting the format of payload to json.
    httpUriRequest.addHeader("Accept", "application/json")
    //println("{\"query\":\"" + query.getQuery() + "\"}")

//    generating the StringEntity out of query which can be set in http request
    val queryPayload = new StringEntity("{\"query\":\"" + query.getQuery() + "\"}", ContentType.APPLICATION_JSON)
    httpUriRequest.setEntity(queryPayload)
    logger.info("Query set to payload.")
    val response = httpclient.execute(httpUriRequest)
    logger.info("Response object received")

//    returning the response in String format
    EntityUtils.toString(response.getEntity, "UTF-8")
  }

  // function to create a file and write the contents into it
  def writeIntoFile(client: String, path: String) = {
    val result_file = new FileWriter(path)
    result_file.write(client)
    result_file.close()
  }
}


// it is use to create the Basic GraphQL client without specifying the token anq query.
// It will create the object in PlainClient state
object GraphQLClient {
  def apply(): GraphQLClient[PlainClient] = new GraphQLClient[PlainClient]("", null)
}