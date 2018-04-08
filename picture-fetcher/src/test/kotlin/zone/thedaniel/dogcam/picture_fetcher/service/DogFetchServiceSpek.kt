package zone.thedaniel.dogcam.picture_fetcher.service

import arrow.data.Try
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import zone.thedaniel.dogcam.DogPhotoServiceGrpc
import zone.thedaniel.dogcam.PhotoRequest
import zone.thedaniel.dogcam.domain.entity.DogImage
import zone.thedaniel.dogcam.domain.service.DogImageRetrievalService
import zone.thedaniel.dogcam.picture_fetcher.givenGrpcServer



private object StubService: DogImageRetrievalService {
    val TEST_RESPONSE = "http://example.org"
    override suspend fun requestImage() = Try.pure(DogImage(TEST_RESPONSE))
}

@RunWith(JUnitPlatform::class)
object DogFetchServiceSpek: Spek({
    givenGrpcServer(DogFetchService(StubService), "Dog Fetch Service") {channel ->
        val stub = DogPhotoServiceGrpc.newBlockingStub(channel)

        describe("requestPhoto") {
            it("should get a photo url") {
                val response = stub.requestPhoto(PhotoRequest.getDefaultInstance())
                response.url shouldEqual StubService.TEST_RESPONSE
            }
        }
    }
})

