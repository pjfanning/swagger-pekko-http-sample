package com.example.pekko.echoenum

import org.apache.pekko.http.scaladsl.server.{Directives, Route}
import com.example.pekko.DefaultJsonFormats
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.{Consumes, POST, Path, Produces}
import spray.json.{DeserializationException, JsString, JsValue, RootJsonFormat}

//case class EchoEnum(@Schema(required = true, `type` = "string", allowableValues = Array("TALL", "GRANDE", "VENTI"))
//                    enumValue: SizeEnum.Value)
private class SizeEnumTypeClass extends TypeReference[SizeEnum.type]
case class EchoEnum(@JsonScalaEnumeration(classOf[SizeEnumTypeClass]) enumValue: SizeEnum.Value)

@Path("/echoenum")
object EchoEnumService extends Directives with DefaultJsonFormats {

  implicit val enumFormat: RootJsonFormat[SizeEnum.Value] =
    new RootJsonFormat[SizeEnum.Value] {
      def write(obj: SizeEnum.Value): JsValue = JsString(obj.toString)
      def read(json: JsValue): SizeEnum.Value = {
        json match {
          case JsString(txt) => SizeEnum.withName(txt)
          case somethingElse => throw DeserializationException(s"Expected a value from enum $SizeEnum instead of $somethingElse")
        }
      }
    }
  implicit val echoEnumFormat: RootJsonFormat[EchoEnum] = jsonFormat1(EchoEnum.apply)

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
