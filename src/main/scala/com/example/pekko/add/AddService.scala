package com.example.pekko.add

import org.apache.pekko.actor.ActorRef
import org.apache.pekko.http.scaladsl.server.{Directives, Route}
import org.apache.pekko.pattern.ask
import org.apache.pekko.util.Timeout
import com.example.pekko.add.AddActor._
import com.github.pjfanning.pekkohttpjackson.JacksonSupport
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.{Consumes, POST, Path, Produces}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

@Path("/add")
class AddService(addActor: ActorRef)(implicit executionContext: ExecutionContext)
  extends Directives with JacksonSupport {

  implicit val timeout: Timeout = Timeout(2.seconds)

  val route: Route = add

  @POST
  @Consumes(Array(MediaType.APPLICATION_JSON))
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Operation(summary = "Add integers", description = "Add integers",
    requestBody = new RequestBody(required = true,
      content = Array(new Content(schema = new Schema(implementation = classOf[AddRequest])))),
    responses = Array(
      new ApiResponse(responseCode = "200", description = "Add response",
        content = Array(new Content(schema = new Schema(implementation = classOf[AddResponse])))),
      new ApiResponse(responseCode = "500", description = "Internal server error"))
  )
  def add: Route =
    path("add") {
      post {
        entity(as[AddRequest]) { request =>
          complete { (addActor ? request).mapTo[AddResponse] }
        }
      }
    }

}
