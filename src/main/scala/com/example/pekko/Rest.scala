package com.example.pekko

import org.apache.pekko.actor.{ActorSystem, Props}
import org.apache.pekko.http.cors.scaladsl.CorsDirectives.cors
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.server.RouteConcatenation
import com.example.pekko.hello.{HelloActor, HelloService}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor, Future}

object Rest extends App with RouteConcatenation {
  implicit val system: ActorSystem = ActorSystem("pekko-http-sample")
  sys.addShutdownHook(system.terminate())

  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val hello = system.actorOf(Props[HelloActor]())

  val routes =
    cors()(new HelloService(hello).route)

  val f = for {
    bindingFuture <- Http().newServerAt("0.0.0.0", 12345).bind(routes)
    waitOnFuture  <- Future.never
  } yield waitOnFuture

  Await.ready(f, Duration.Inf)
}
