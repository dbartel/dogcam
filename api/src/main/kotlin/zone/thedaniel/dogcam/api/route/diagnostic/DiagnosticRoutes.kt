package zone.thedaniel.dogcam.api.route.diagnostic


import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes


interface DiagnosticApi {
    val applicationVersion: String

    fun Request.ping() = Response(OK).body("pong!")
    fun Request.version() = Response(OK).body(applicationVersion)
}

fun DiagnosticApi.diagnosticRoutes() = routes(
        "/ping" bind GET to { request -> request.ping() },
        "/version" bind GET to { request -> request.version() }
)




