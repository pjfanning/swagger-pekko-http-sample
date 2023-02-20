package com.example.pekko.echoenum

import org.apache.pekko.http.scaladsl.server.{Directives, Route}
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import com.github.pjfanning.pekkohttpjackson.JacksonSupport
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.{Consumes, POST, Path, Produces}

@Path("/echoenum")
object EchoEnumService extends Directives with JacksonSupport {

  //case class EchoEnum(@Schema(required = true, `type` = "string", allowableValues = Array("TALL", "GRANDE", "VENTI"))
  //                    enumValue: SizeEnum.Value)
  class SizeEnumTypeClass extends TypeReference[SizeEnum.type]
  case class EchoEnum(@JsonScalaEnumeration(classOf[SizeEnumTypeClass]) enumValue: SizeEnum.Value)

  val route: Route = echo

  @POST
  @Consumes(Array(MediaType.APPLICATION_JSON))
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Operation(summary = "Echo Enum", description = "Echo Enum",
    requestBody = new RequestBody(required = true,
      content = Array(new Content(schema = new Schema(implementation = classOf[EchoEnum])))),
    responses = Array(
      new ApiResponse(responseCode = "200", description = "Echo Enum",
        content = Array(new Content(schema = new Schema(implementation = classOf[EchoEnum])))),
      new ApiResponse(responseCode = "400", description = "Bad Request"))
  )
  def echo: Route =
    path("echoenum") {
      post {
        entity(as[EchoEnum]) { request =>
          complete(request)
        }
      }
    }

}
