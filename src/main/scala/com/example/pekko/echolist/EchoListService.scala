package com.example.pekko.echolist

import org.apache.pekko.http.scaladsl.server.{Directives, Route}
import com.example.pekko.DefaultJsonFormats
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.{Consumes, POST, Path, Produces}
import pl.iterators.kebs.sprayjson.KebsSprayJson
import spray.json.RootJsonFormat

case class EchoList(listName: String, values: Seq[String])

@Path("/echolist")
object EchoListService extends Directives with DefaultJsonFormats with KebsSprayJson {

  implicit val echoListFormat: RootJsonFormat[EchoList] = jsonFormatN[EchoList]

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
