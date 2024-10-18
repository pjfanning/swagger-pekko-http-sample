package com.example.pekko.hello

import org.apache.pekko.http.scaladsl.server.{Directives, Route}

import scala.concurrent.ExecutionContext

class HelloService()(implicit executionContext: ExecutionContext)
  extends Directives {
  
  val route: Route = getHello

  def getHello: Route =
    path("hello") {
      get {
        complete("Hello")
      }
    }
}

