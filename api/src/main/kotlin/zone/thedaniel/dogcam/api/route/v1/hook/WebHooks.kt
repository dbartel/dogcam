package zone.thedaniel.dogcam.api.route.v1.hook

import org.http4k.routing.bind
import org.http4k.routing.routes
import zone.thedaniel.dogcam.api.route.v1.hook.slack.SlackApi
import zone.thedaniel.dogcam.api.route.v1.hook.slack.slackHooks


interface WebHookApi : SlackApi

fun WebHookApi.webHooks() = routes(
        "/slack" bind slackHooks()
)
