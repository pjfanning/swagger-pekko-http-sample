package com.example.pekko.hello

import org.apache.pekko.actor.ActorRef
import org.apache.pekko.http.scaladsl.server.{Directives, Route}
import org.apache.pekko.pattern.ask
import org.apache.pekko.util.Timeout
import com.example.pekko.hello.HelloActor._
import com.github.pjfanning.pekkohttpjackson.JacksonSupport
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.{Operation, Parameter}
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.{GET, Path, Produces}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

@Path("/hello")
class HelloService(hello: ActorRef)(implicit executionContext: ExecutionContext)
  extends Directives with JacksonSupport {

  implicit val timeout: Timeout = Timeout(2.seconds)

  val route: Route =
    getHello ~
    getHelloSegment

  @GET
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Operation(summary = "Return Hello greeting (anonymous)", description = "Return Hello greeting for anonymous request",
    responses = Array(
      new ApiResponse(responseCode = "200", description = "Hello response",
        content = Array(new Content(schema = new Schema(implementation = classOf[Greeting])))),
      new ApiResponse(responseCode = "500", description = "Internal server error"))
  )
  def getHello: Route =
    path("hello") {
      get {
        complete { (hello ? AnonymousHello).mapTo[Greeting] }
      }
    }

  @GET
  @Path("{name}") //the token inside the curly brackets should match the parameter name in the @Operation annotation
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Operation(summary = "Return Hello greeting", description = "Return Hello greeting for named user",
    parameters = Array(new Parameter(name = "name", in = ParameterIn.PATH, description = "user name")),
    responses = Array(
      new ApiResponse(responseCode = "200", description = "Hello response",
        content = Array(new Content(schema = new Schema(implementation = classOf[Greeting])))),
      new ApiResponse(responseCode = "500", description = "Internal server error"))
  )
  def getHelloSegment: Route =
    path("hello" / Segment) { name =>
      get {
        complete { (hello ? Hello(name)).mapTo[Greeting] }
      }
    }
}

