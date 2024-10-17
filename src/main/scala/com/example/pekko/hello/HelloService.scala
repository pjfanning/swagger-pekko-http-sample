package com.example.pekko.hello

import org.apache.pekko.actor.ActorRef
import org.apache.pekko.http.scaladsl.server.{Directives, Route}
import org.apache.pekko.pattern.ask
import org.apache.pekko.util.Timeout
import com.example.pekko.DefaultJsonFormats
import com.example.pekko.hello.HelloActor._
import org.slf4j.LoggerFactory
import spray.json.RootJsonFormat

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

class HelloService(hello: ActorRef)(implicit executionContext: ExecutionContext)
  extends Directives with DefaultJsonFormats {

  private val logger = LoggerFactory.getLogger(classOf[HelloService])

  implicit val timeout: Timeout = Timeout(2.seconds)
  implicit val greetingFormat: RootJsonFormat[Greeting] = jsonFormat1(Greeting.apply)

  val route: Route = getHello

  def getHello: Route =
    path("hello") {
      get {
        logger.info("received anonymous hello request")
        try {
          complete {
            (hello ? AnonymousHello).mapTo[Greeting]
          }
        } catch {
          case t: Throwable =>
            logger.error("Hello call failed", t)
            throw t
        }
      }
    }
}

