package com.example.pekko

import org.apache.pekko.actor.{ActorSystem, Props}
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.server.RouteConcatenation
//import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import com.example.pekko.add.{AddActor, AddService}
import com.example.pekko.addoption.{AddOptionActor, AddOptionService}
import com.example.pekko.echoenum.EchoEnumService
import com.example.pekko.echoenumeratum.EchoEnumeratumService
import com.example.pekko.echolist.EchoListService
import com.example.pekko.hello.{HelloActor, HelloService}
import com.example.pekko.swagger.SwaggerDocService

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor, Future}

object Rest extends App with RouteConcatenation {
  implicit val system: ActorSystem = ActorSystem("pekko-http-sample")
  sys.addShutdownHook(system.terminate())

  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val add = system.actorOf(Props[AddActor])
  val addOption = system.actorOf(Props[AddOptionActor])
  val hello = system.actorOf(Props[HelloActor])

  //TODO add back CORS support
  val routes =
    new AddService(add).route ~
      new AddOptionService(addOption).route ~
      new HelloService(hello).route ~
      EchoEnumService.route ~
      EchoEnumeratumService.route ~
      EchoListService.route ~
      SwaggerDocService.routes

  val f = for {
    bindingFuture <- Http().newServerAt("0.0.0.0", 12345).bind(routes)
    waitOnFuture  <- Future.never
  } yield waitOnFuture

  Await.ready(f, Duration.Inf)
}
