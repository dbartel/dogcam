package zone.thedaniel.dogcam.picture_fetcher.service

import arrow.data.recover
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.experimental.launch
import zone.thedaniel.dogcam.CameraGrpc
import zone.thedaniel.dogcam.DogPhotoServiceGrpc
import zone.thedaniel.dogcam.PhotoRequest
import zone.thedaniel.dogcam.PhotoResponse
import zone.thedaniel.dogcam.domain.service.DogImageRetrievalService
import zone.thedaniel.dogcam.proto.ext.toPhotoResponse

class DogFetchService(val service: DogImageRetrievalService): DogPhotoServiceGrpc.DogPhotoServiceImplBase() {
    override fun requestPhoto(request: PhotoRequest?, responseObserver: StreamObserver<PhotoResponse>?) {
        launch {
            service.requestImage()
                    .map { response ->
                        responseObserver?.run {
                            onNext(response.toPhotoResponse())
                            onCompleted()
                        }
                    }
                    .recover {
                        responseObserver?.onError(it)
                    }
        }
    }
}
