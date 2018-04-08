package zone.thedaniel.dogcam.infrastructure.service

import arrow.data.Try
import arrow.effects.IO
import kotlinx.coroutines.experimental.runBlocking
import zone.thedaniel.dogcam.DogPhotoServiceGrpc
import zone.thedaniel.dogcam.domain.entity.DogImage
import zone.thedaniel.dogcam.domain.service.DogImageRetrievalService
import zone.thedaniel.dogcam.proto.ext.suspendRequestPhoto
import zone.thedaniel.dogcam.proto.ext.toDogImage

class DogGrpcService(private val stub: DogPhotoServiceGrpc.DogPhotoServiceStub): DogImageRetrievalService {
    suspend override fun requestImage() = Try {
        stub.suspendRequestPhoto().toDogImage()
    }
}