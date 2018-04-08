package zone.thedaniel.dogcam.infrastructure.service

import arrow.data.Try
import zone.thedaniel.dogcam.domain.entity.DogImage
import zone.thedaniel.dogcam.domain.service.ImageNotificationService
import zone.thedaniel.dogcam.domain.service.NotificationUrl
import zone.thedaniel.dogcam.infrastructure.api.SlackResponseApi
import zone.thedaniel.dogcam.infrastructure.api.toSlackMessage

class NotificationRetrofitService: ImageNotificationService {
    private val api: SlackResponseApi = SlackResponseApi.singleton

    suspend override fun notify(image: DogImage, notifyUrl: NotificationUrl) = Try {
        api.respond(image.toSlackMessage(), notifyUrl.getEndpoint()).await()
        Unit
    }
}

internal fun NotificationUrl.getEndpoint() = replace("https://hooks.slack.com/commands/", "")
