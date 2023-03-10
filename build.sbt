name := "swagger-pekko-http-sample"

scalaVersion := "2.13.10"

val pekkoVersion = "0.0.0+26592-864ee821-SNAPSHOT"
val pekkoHttpVersion = "0.0.0+4284-374ff95e-SNAPSHOT"
val jacksonVersion = "2.14.2"
val swaggerVersion = "2.2.8"

resolvers ++= Resolver.sonatypeOssRepos("snapshots")
resolvers += "Apache Snapshots" at "https://repository.apache.org/content/groups/snapshots"

val swaggerDependencies = Seq(
  "jakarta.ws.rs" % "jakarta.ws.rs-api" % "3.0.0",
  "com.github.swagger-akka-http" %% "swagger-pekko-http" % "2.10.0+13-ec2a1564-SNAPSHOT",
  "com.github.swagger-akka-http" %% "swagger-scala-module" % "2.9.0",
  "com.github.swagger-akka-http" %% "swagger-enumeratum-module" % "2.6.1",
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
  "org.slf4j" % "slf4j-simple" % "2.0.6"
) ++ swaggerDependencies
