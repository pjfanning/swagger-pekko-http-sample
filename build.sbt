name := "swagger-pekko-http-sample"

scalaVersion := "3.3.4"

val pekkoVersion = "1.1.2"
val pekkoHttpVersion = "1.1.0"
val slf4jVersion = "2.0.16"

libraryDependencies ++= Seq(
  "org.apache.pekko" %% "pekko-http" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-http-cors" % pekkoHttpVersion,
  "org.apache.pekko" %% "pekko-actor" % pekkoVersion,
  "org.apache.pekko" %% "pekko-stream" % pekkoVersion,
  "org.apache.pekko" %% "pekko-slf4j" % pekkoVersion,
  "org.slf4j" % "slf4j-simple" % slf4jVersion
)
