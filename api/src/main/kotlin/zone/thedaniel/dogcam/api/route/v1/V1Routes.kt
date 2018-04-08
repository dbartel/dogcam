package zone.thedaniel.dogcam.api.route.v1

import org.http4k.routing.bind
import org.http4k.routing.routes
import zone.thedaniel.dogcam.api.route.v1.hook.WebHookApi
import zone.thedaniel.dogcam.api.route.v1.hook.webHooks


interface V1Api : WebHookApi

fun V1Api.v1Routes() = routes(
    "/hook" bind webHooks()
)
