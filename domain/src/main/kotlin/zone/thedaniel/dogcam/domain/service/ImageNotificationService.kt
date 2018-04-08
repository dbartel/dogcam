package zone.thedaniel.dogcam.domain.service

import arrow.data.Try
import zone.thedaniel.dogcam.domain.entity.DogImage

interface ImageNotificationService {
    suspend fun notify(image: DogImage, notifyUrl: NotificationUrl): Try<Unit>
}

typealias NotificationUrl = String


