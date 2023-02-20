package com.example.pekko.echoenumeratum

import com.github.pjfanning.pekkohttpjackson.JacksonSupport
import org.apache.pekko.http.scaladsl.server.{Directives, Route}
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.{Consumes, POST, Path, Produces}

@Path("/echoenumeratum")
object EchoEnumeratumService extends Directives with JacksonSupport {

  case class EchoEnumeratum(enumValue: SizeEnum)

  val route: Route = echo

  @POST
  @Consumes(Array(MediaType.APPLICATION_JSON))
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Operation(summary = "Echo Enumeratum", description = "Echo Enumeratum",
    requestBody = new RequestBody(required = true,
      content = Array(new Content(schema = new Schema(implementation = classOf[EchoEnumeratum])))),
    responses = Array(
      new ApiResponse(responseCode = "200", description = "Echo Enumeratum",
        content = Array(new Content(schema = new Schema(implementation = classOf[EchoEnumeratum])))),
      new ApiResponse(responseCode = "400", description = "Bad Request"))
  )
  def echo: Route =
    path("echoenumeratum") {
      post {
        entity(as[EchoEnumeratum]) { request =>
          complete(request)
        }
      }
    }

}
