package zone.thedaniel.dogcam.api.route

import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import zone.thedaniel.dogcam.api.route.diagnostic.DiagnosticApi
import zone.thedaniel.dogcam.api.route.diagnostic.diagnosticRoutes
import zone.thedaniel.dogcam.api.route.v1.V1Api
import zone.thedaniel.dogcam.api.route.v1.v1Routes

internal interface Api : DiagnosticApi, V1Api {
    val isDebug: Boolean
}

private fun Api.applicationRoutes() = routes(
        "/diagnostic" bind diagnosticRoutes(),
        "/api/v1" bind v1Routes()
)

internal fun Api.routes()  = when(isDebug) {
    true -> DebuggingFilters.PrintRequestAndResponse().then(applicationRoutes())
    false -> routes(applicationRoutes())
}
