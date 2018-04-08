package zone.thedaniel.dogcam.proto.ext

import io.grpc.stub.StreamObserver
import zone.thedaniel.dogcam.DogPhotoServiceGrpc
import zone.thedaniel.dogcam.PhotoRequest
import zone.thedaniel.dogcam.PhotoResponse
import zone.thedaniel.dogcam.domain.entity.DogImage
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.suspendCoroutine


suspend fun DogPhotoServiceGrpc.DogPhotoServiceStub.suspendRequestPhoto(request: PhotoRequest = PhotoRequest.getDefaultInstance()) =
        suspendCoroutine<PhotoResponse> {continuation ->
            val observer = continuation.toSingleObserver()
            requestPhoto(request, observer)
        }

fun <T:Any> Continuation<T>.toSingleObserver() = object : StreamObserver<T> {
    lateinit var value: T

    override fun onNext(value: T) {
        this.value = value
    }

    override fun onError(t: Throwable?) {
        resumeWithException(t ?: IllegalStateException("GRPC Failed"))
    }

    override fun onCompleted() {
        resume(value)
    }
}

fun PhotoResponse.toDogImage() = DogImage(url)
fun DogImage.toPhotoResponse() = PhotoResponse.newBuilder()
        .setUrl(url)
        .build()