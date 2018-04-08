package zone.thedaniel.dogcam.infrastructure.service

import arrow.data.Try
import arrow.effects.IO
import kotlinx.coroutines.experimental.runBlocking
import zone.thedaniel.dogcam.domain.entity.DogImage
import zone.thedaniel.dogcam.domain.service.DogImageRetrievalService
import zone.thedaniel.dogcam.infrastructure.api.DogApi

class DogRetrofitService: DogImageRetrievalService {
    private val api: DogApi = DogApi.singleton

    suspend override fun requestImage() = Try {
            val imageAsync = api.getImage()
            DogImage(imageAsync.await().message)
    }
}