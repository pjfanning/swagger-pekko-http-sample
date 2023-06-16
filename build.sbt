name := "swagger-pekko-http-sample"

scalaVersion := "2.13.11"
//scalaVersion := "3.3.0"

val pekkoVersion = "0.0.0+26720-01379c41-SNAPSHOT"
val pekkoHttpVersion = "0.0.0+4431-0dc1da23-SNAPSHOT"
val jacksonVersion = "2.15.2"
val swaggerVersion = "2.2.12"

resolvers ++= Resolver.sonatypeOssRepos("snapshots")
resolvers += "Apache Snapshots" at "https://repository.apache.org/content/groups/snapshots"

val swaggerDependencies = Seq(
  "jakarta.ws.rs" % "jakarta.ws.rs-api" % "3.0.0",
  "com.github.swagger-akka-http" %% "swagger-pekko-http" % "2.10.0+37-00f43374-SNAPSHOT",
  "com.github.swagger-akka-http" %% "swagger-scala-module" % "2.11.0",
  "com.github.swagger-akka-http" %% "swagger-enumeratum-module" % "2.8.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "io.swagger.core.v3" % "swagger-jaxrs2-jakarta" % swaggerVersion
)

libraryDependencies ++= Seq(
  "pl.iterators" %% "kebs-spray-json" % "1.9.5",
  "org.apache.pekko" %% "pekko-http" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-http-spray-json" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-actor" % pekkoVersion,
  "org.apache.pekko" %% "pekko-stream" % pekkoVersion,
  "org.apache.pekko" %% "pekko-slf4j" % pekkoVersion,
  "ch.megard" %% "pekko-http-cors" % "0.0.0-SNAPSHOT",
  "org.slf4j" % "slf4j-simple" % "2.0.7"
) ++ swaggerDependencies
