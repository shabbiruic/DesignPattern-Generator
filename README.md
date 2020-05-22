## UIC CS474 - Object Oriented Languages and Environments

## Course Project - Create an object-oriented pure functional design and implementation of a [GraphQL](https://graphql.org/) client framework for [Github](https://github.com/) as an I/O monad.

## Overview

In this project we aim to design, build and analyze the implementation of a program that forms [GraphQL](https://graphql.org/) queries, extracts and organizes git repositories data from [Github](https://github.com/) by defining models which store the response of these queries.

## Team Members

- Rahul Keswani (rromil2@uic.edu)
- Shabbir Bohra (sbohra3@uic.edu)
- Mehul Birari (mbirar2@uic.edu)

### What is GraphQL?

GraphQL is an open-source data query and manipulation language for APIs, and a runtime for fulfilling queries with existing data. GraphQL provides a complete and understandable description of the data in your API, gives clients the power to ask for exactly what they need and nothing more, makes it easier to evolve APIs over time, and enables powerful developer tools.


### Features
- You can build the GraphQL github command in **Object Oriented form**.
- It checks implicitly at **compile time** itself whether the query formed is correct or not using the **Phantom Type**.
- It is not mandatory to specify the various parameters of query like filters and fields in any specific order.
- User can build complex Composite Queries till any level.
- Query is built using **builder pattern** which eases the query building.
- It uses the Composite design Pattern for the Construction of nested Queries.
- Call goes to Server only when query is built completely or else user would not be able to make a call.
- You can in turn filter the result obtained after the API hit to fetch values that are required by the user.

#### Few sample queries that can be formed along with its object oriented query are : 


- Sample Query: 1
``` 

{
	user( login :"<name>")
	{ repositories
		{ totalCount 
		} 
	}
}

```
**Object Oriented Form:** 
```
User().setFilter(login("<name>")).setField(Repositories().setField(TotalCount()).buildAsUserField).getBaseQuery 
```


- Sample Query: 2
```
user(login: "<UserInput>")
      {
        followers(first: 100)
        {
          totalCount,
          edges
          {
            node
            {
              name,email,company,login
            }
          }
        }
      }

``` 
**Object Oriented Form:** 
``` 
User().setFilter(login("<UserInput>")).setField(Followers().setFilter(first(100)).setField(TotalCount()).setField(FollowerEdgeNode().setField(Name()).setField(Email()).setField(Company()).setField(Login()).buildAsFollowerField).buildAsUserField).getBaseQuery
```


- Sample Query: 3
```
repository( name : "scrapy" , owner : "scrapy" )
  { collaborators
    { edges 
      { node 
        { email , name
        }
      } 
    } 
  }

```
**Object Oriented Form:** 
```
Repository().setFilter(owner("scrapy")).setFilter(name("scrapy")).setField(Collaborator().setField(CollaboratorEdgeNode().setField(Name()).setField(Email()).buildAsCollaboratorField).buildAsRepositoryField).getBaseQuery

```

### Implementation Details
- Filters are the values which a user passes to constrain the output data.
- Fields means the properties that user wants in the output. 
- A Field can be a query itself, we call it a composite field.
- Each Query class consists of a list of filters and a list of fields.
- Every Query class inherits the **Query** class which provides the logic for query construction and other method signature.
- Only those queries which inherit the **RootQuery** can act as a starting point for query formation in our case **User, Repository & Search**
- **Composite** pattern is used for Query Construction i.e. every nested query is responsible for its own query formation.
- **Phantom** type is used for checking validity of query formation during compile time.
- You can only hit the query from the GraphQL Client when it is formed completely and all other details like token are also passed.
- For Checking that only Specific fields are allowed for specific query it uses trait for grouping.
- For Checking that only Specific Filters are allowed for specific query it uses trait.


#### Structure of **Query** Class

``` scala

abstract class Query[State, A <: Filters.ParentFilter, F <: Fields.Field, ExecutableState](filters: List[A], fields: List[F]) {

  // building query only when it is in its executable state
  def build(implicit state: State =:= ExecutableState): Query[State, A, F, ExecutableState]

  // Method to construct query out of it
  def getQuery(queryName: String): 

  // Method which child class overrrides to provide name of query to be used for its construction
  def getQuery: String

  // adding filters to query
  def setFilter(filter: A): Query[State, A, F, ExecutableState]

  // adding fields to query and changing its state to FieldSpecified
  def setField(field: F): Query[State with FieldSpecified, A, F, ExecutableState]


  // Method to convert list of filters in the GraphQl query format
  def getFilters: String 
  
  // Method to construct the query fields in GraphQl query format
  def getFields: String

}
```

#### Structure of Parent Filter class
```scala

trait ParentFilter {

  def getFilter():String

}

class Filter[A,B](value:A,key:B) extends ParentFilter {

    // method to provide the filter in query form.
    def getFilter(): String =
      value match{
        case i:Int => key+" : " + i
        case s:String => key+" : " + "\\\"" +s+"\\\""
      }

  }

```

#### Structure of Field class
``` scala

case class <Field Class Name>(fieldName:String="<FieldName>") extends <traits of class for which it can be field>   {

  override def buildField(): String =
    fieldName

}
```
### Information about Queries we support
 
#### Base Queries 
##### these are the Queries which can act as a starting point for Query Construction they are:
 -   User
 -  Search
 -  Repository
 
#### Queries along with its Fields and Filter which are supported.
a).  **User**
##### Fields
 -  Follower(Composite Field)
 -  Repositories(Composite Field)
 -  Repository(Composite Field)
 -  Name(Scalar Field)
 
##### Filters
 -  first(INT)
 -  last(INT)
 -  login(String)(Mandatory Filter)
 
b).  **Search**
##### Fields
 -  RepositoryCount(Scalar Field)
##### Filters
 -  First(INT)
 -  Last(INT)
 -  after(String)
 -  before(String)
 -  objectType(String)(Mandatory Filter)
 -  query(String)(Mandatory Filter)
 
c). **Repository**
##### Fields
 -  TotalCount(Scalar Field)
 -  Collaborators(Composite Field)
 -  Name(Scalar Field)
##### Filters
 -  name(String)(Mandatory Filter)
 -  owner(String)Mandatory Filter)
 
d).  **Repositories**
##### Fields
 -  TotalCount(Scalar)
##### Filters
 -  first(INT)
 -  last(INT)
 
e).  **Follower**:  At least one Filter is mandatory 
##### Fields
 -  FollowerEdgeNode(Composite Field)
 - TotalCount(Scalar Field)
##### Filters 
 - first(INT)
 - last(INT)

f). **FollowerEdgeNode**
##### Fields
 -  Login(Scalar Field)
 -  Name(Scalar Field)

g).  **Collaborator**
##### Fields
 -  CollaboratorEdgeNode(Composite Field)
 -  Name(Scalar Field)
##### Filters
 -  first(INT)
 -  last(INT)
 -  login(String)
 -  name(String)
 -  owner(String)
 
h).  **CollaboratorEdgeNode**
##### Fields
 -  Email(Scalar Field)
 -  Login(Scalar Field)


#### Sample for the filters that can be attached to the result obtained

Result obtained and stored in a variable named **'client'** :-
```
{
  "data": {
    "user": {
      "repositories": {
        "totalCount": 6
      }
    }
  }
}

```

For printing the total Count of the repositories :- 

```
val filtered_client = FilterConverter().filtercon(client)
val filter_result = FilterConverter().filter(filtered_client.user.repositories.totalCount)
```


## Instructions to run the Project

### Prerequisites

- Your workstation should have [SBT](https://www.scala-sbt.org/) installed.
- You should have a [Github Developer account](https://developer.github.com/v4/) account.
- You should have generated a [personal access token](https://github.com/settings/tokens) for your Github account.

### Steps to run the project via SBT shell

- Clone this private repository.
- Open the sbt shell and navigate to the path as the local repository's path.

#### Compile and Test
- Type the following command in the sbt shell in order to build the project and run unit tests.

```
sbt clean compile test
```

#### Execute the program

- Build your own query in the Object Oriented form shown above in the **QueryExecutor.scala** file.
- It is mandatory that query starts from Base query else you can't pass it to the GraphQL Client for fetching result.
- It is mandatory that Query have atleast one Scaler Field present in it else you can;t build it.
- For building certain Queries for which some filters are mandatory you can't build it unless those filters are specified.
- Filters can be specified in any order.
- Fields can also be specified in any order.
- For Composite Query after its completion you have to convert it into field of its parent query else you can't build it.
- Pass your [personal access token](https://github.com/settings/tokens) parameter in the **application.conf** file.
- Pass the query that you formed in the **setQuery()** function call as shown in the existing sample. 
- Add the required filters needed for the result to be shown according to the requirements.
- See the already present **QueryExecutor.scala** file for understanding the flow and steps to build a query and set appropriate filters.
- Type the following command in the sbt shell to run the program.

```
sbt run
```

- Check the console for logs and the **result.json** file for the output of the GraphQL Query and the **filtered_result** file for the filtered result.
- **NOTE :-** If the project doesnt compile via sbt, run the **QueryExecutor.scala** file via IntelliJ.
- Done.

## Architecture of the designed GraphQL Client 

- The following diagram shows the Field Dependency for the fields used in the **object Oriented** Query construction.

!["Field Dependency"](https://bitbucket.org/mehulbirari/mehul_birari_cs474_courseproject/raw/aab17082934ff7d84616e2849e015355e597e3d8/GraphQL_ObjectOriented_Implementation/Screenshots/Fields%20dependency.PNG)

- The following diagram shows the Filter Dependency for the filters used in the **object Oriented** Query construction.

!["Filter Dependency"](https://bitbucket.org/mehulbirari/mehul_birari_cs474_courseproject/raw/aab17082934ff7d84616e2849e015355e597e3d8/GraphQL_ObjectOriented_Implementation/Screenshots/Filters%20dependency.PNG)

- The following diagram shows the BaseQueries class dependency which are used to create Queries.

!["Queries Dependency"](https://bitbucket.org/mehulbirari/mehul_birari_cs474_courseproject/raw/aab17082934ff7d84616e2849e015355e597e3d8/GraphQL_ObjectOriented_Implementation/Screenshots/Queries%20dependency.PNG)

- The following diagram shows the State Dependency of the Query to ensure proper execution regardless of the field and filter specification.   

!["State Dependency"](https://bitbucket.org/mehulbirari/mehul_birari_cs474_courseproject/raw/aab17082934ff7d84616e2849e015355e597e3d8/GraphQL_ObjectOriented_Implementation/Screenshots/State%20dependency.PNG)

- The following diagram shows the Converter Dependency for json String to Scala object and vice versa.

!["Converter Dependency"](https://bitbucket.org/mehulbirari/mehul_birari_cs474_courseproject/raw/aab17082934ff7d84616e2849e015355e597e3d8/GraphQL_ObjectOriented_Implementation/Screenshots/Coverters%20dependency.PNG) 



### Limitations
- The scope for Query Construction can be increased.
- Filters for the result are limited to singular values.

### Improvements

- Write more tests for Unit testing.
- Have more functionalities for GraphQL Query construction.
- Add function for multiple filter attachments to the result. 
