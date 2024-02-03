name := "swagger-pekko-http-sample"

scalaVersion := "2.13.12"

val pekkoVersion = "1.0.2"
val pekkoHttpVersion = "1.0.0"
val jacksonVersion = "2.16.1"
val swaggerVersion = "2.2.20"

//resolvers ++= Resolver.sonatypeOssRepos("snapshots")
//resolvers += "Apache Snapshots" at "https://repository.apache.org/content/groups/snapshots"

val swaggerDependencies = Seq(
  "jakarta.ws.rs" % "jakarta.ws.rs-api" % "3.0.0",
  "com.github.swagger-akka-http" %% "swagger-pekko-http" % "2.12.0",
  "com.github.swagger-akka-http" %% "swagger-scala-module" % "2.12.2",
  "com.github.swagger-akka-http" %% "swagger-enumeratum-module" % "2.9.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "io.swagger.core.v3" % "swagger-jaxrs2-jakarta" % swaggerVersion
)

libraryDependencies ++= Seq(
  "pl.iterators" %% "kebs-spray-json" % "1.9.7",
  "org.apache.pekko" %% "pekko-http" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-http-spray-json" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-http-cors" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-actor" % pekkoVersion,
  "org.apache.pekko" %% "pekko-stream" % pekkoVersion,
  "org.apache.pekko" %% "pekko-slf4j" % pekkoVersion,
  "org.slf4j" % "slf4j-simple" % "2.0.11"
) ++ swaggerDependencies
