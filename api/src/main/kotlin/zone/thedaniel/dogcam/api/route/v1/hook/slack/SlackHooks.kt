package zone.thedaniel.dogcam.api.route.v1.hook.slack

import arrow.data.*
import kotlinx.coroutines.experimental.launch
import org.http4k.core.Body
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.lens.FormField
import org.http4k.lens.Validator
import org.http4k.lens.string
import org.http4k.lens.webForm
import org.http4k.routing.bind
import org.http4k.routing.routes
import zone.thedaniel.dogcam.domain.ext.error
import zone.thedaniel.dogcam.domain.ext.sFlatMap
import zone.thedaniel.dogcam.domain.service.DogImageRetrievalService
import zone.thedaniel.dogcam.domain.service.ImageNotificationService


interface SlackApi {
    val dogRetrievalService: DogImageRetrievalService
    val notificationService: ImageNotificationService

    fun Request.requestDogPic(): Response {
        val result = toSlackCommand()
            .map { cmd ->
                launch {
                    dogRetrievalService.requestImage()
                            .sFlatMap {
                                notificationService.notify(it, cmd.response_url)
                            }
                            .recover {
                                "Webhook execution failed: ${it.message}".error()
                            }
                }
                Response(Status.OK)
            }

        return when(result) {
            is Success -> Response(Status.OK)
            is Failure -> Response(Status.BAD_REQUEST)
        }
    }
}

data class SlackHookCmd(
        val response_url:String
)

fun Request.toSlackCommand(): Try<SlackHookCmd> = Try {
    val urlField = FormField.string().required("response_url")
    val form = Body.webForm(Validator.Strict, urlField).toLens()

    val url = urlField.extract(form.extract(this))
    SlackHookCmd(
            response_url = url
    )
}


fun SlackApi.slackHooks() = routes(
        "/dog" bind POST to { it.requestDogPic() }
)