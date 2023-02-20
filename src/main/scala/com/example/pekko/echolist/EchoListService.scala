package com.example.pekko.echolist

import com.github.pjfanning.pekkohttpjackson.JacksonSupport
import org.apache.pekko.http.scaladsl.server.{Directives, Route}
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.{Consumes, POST, Path, Produces}

@Path("/echolist")
object EchoListService extends Directives with JacksonSupport {

  case class EchoList(listName: String, values: Seq[String])

  val route: Route = echo

  @POST
  @Consumes(Array(MediaType.APPLICATION_JSON))
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Operation(summary = "Echo List", description = "Echo List",
    requestBody = new RequestBody(required = true,
      content = Array(new Content(schema = new Schema(implementation = classOf[EchoList])))),
    responses = Array(
      new ApiResponse(responseCode = "200", description = "Echo List",
        content = Array(new Content(schema = new Schema(implementation = classOf[EchoList])))),
      new ApiResponse(responseCode = "400", description = "Bad Request"))
  )
  def echo: Route =
    path("echolist") {
      post {
        entity(as[EchoList]) { request =>
          complete(request)
        }
      }
    }

}
