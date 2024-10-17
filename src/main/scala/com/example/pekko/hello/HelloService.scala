package com.example.pekko.hello

import org.apache.pekko.http.scaladsl.server.{Directives, Route}
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext

class HelloService()(implicit executionContext: ExecutionContext)
  extends Directives {

  private val logger = LoggerFactory.getLogger(classOf[HelloService])

  val route: Route = getHello

  def getHello: Route =
    path("hello") {
      get {
        complete("Hello")
      }
    }
}

