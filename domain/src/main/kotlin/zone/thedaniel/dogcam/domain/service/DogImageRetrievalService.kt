package zone.thedaniel.dogcam.domain.service

import arrow.data.Try
import arrow.effects.IO
import zone.thedaniel.dogcam.domain.entity.DogImage

interface DogImageRetrievalService {
    suspend fun requestImage(): Try<DogImage>
}

