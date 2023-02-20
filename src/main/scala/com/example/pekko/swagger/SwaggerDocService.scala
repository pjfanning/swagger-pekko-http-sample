package com.example.pekko.swagger

import com.github.swagger.pekko.model.Info
import com.example.pekko.add.AddService
import com.example.pekko.addoption.AddOptionService
import com.example.pekko.echoenum.EchoEnumService
import com.example.pekko.echoenumeratum.EchoEnumeratumService
import com.example.pekko.echolist.EchoListService
import com.example.pekko.hello.HelloService
import io.swagger.v3.oas.models.ExternalDocumentation

/**
 * Sample SwaggerDocService, replace values with those applicable your application.
 * By default, a swagger UI is made available too on the default routes. If you don't need the UI, or want
 * to load the UI in another way, replace [[SwaggerHttpWithUiService]] with [[com.github.swagger.pekko.SwaggerHttpService]]
 */
object SwaggerDocService extends SwaggerHttpWithUiService {
  override val apiClasses = Set(classOf[AddService], classOf[AddOptionService], classOf[HelloService],
    EchoEnumService.getClass, EchoEnumeratumService.getClass, EchoListService.getClass)
  override val host = "localhost:12345"
  override val info: Info = Info(version = "1.0")
  override val externalDocs: Option[ExternalDocumentation] = Some(new ExternalDocumentation().description("Core Docs").url("http://acme.com/docs"))
  //use io.swagger.v3.oas.models.security.SecurityScheme to document authn requirements for API
  //override val securitySchemeDefinitions = Map("basicAuth" -> new SecurityScheme())
  override val unwantedDefinitions = Seq("Function1", "Function1RequestContextFutureRouteResult")
}
