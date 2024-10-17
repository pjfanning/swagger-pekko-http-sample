package com.example.pekko.echoenumeratum

import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.apache.pekko.http.scaladsl.server.{Directives, Route}
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.{Consumes, POST, Path, Produces}
import pl.iterators.kebs.enumeratum.KebsEnumeratum
import pl.iterators.kebs.sprayjson.{KebsSprayJson, KebsSprayJsonEnums}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

@Path("/echoenumeratum")
object EchoEnumeratumService extends Directives with SprayJsonSupport with DefaultJsonProtocol
  with KebsSprayJson with KebsSprayJsonEnums with KebsEnumeratum {

  case class EchoEnumeratum(enumValue: SizeEnum)

  implicit val echoEnumeratumFormat: RootJsonFormat[EchoEnumeratum] = jsonFormatN[EchoEnumeratum]

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
