name := "GraphQL_ObjectOriented_Implementation"

version := "0.1"

scalaVersion := "2.12.8"
libraryDependencies += "org.scalaj" % "scalaj-http_2.11" % "2.3.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.7.4"
libraryDependencies +=  "org.apache.httpcomponents" % "httpclient" % "4.5.10"
libraryDependencies += "com.typesafe" % "config" % "1.4.0"
libraryDependencies += "net.liftweb" %% "lift-json" % "3.3.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.1"
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.25"
libraryDependencies +=  "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies ++= Seq(
  "junit" % "junit" % "4.12" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test exclude("junit", "junit-dep")
)

