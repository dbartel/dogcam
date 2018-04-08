package zone.thedaniel.dogcam.api.route.v1.hook.slack


import arrow.data.Try
import arrow.effects.IO
import io.restassured.RestAssured.given
import io.restassured.RestAssured.post
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import zone.thedaniel.dogcam.api.givenRoutes
import zone.thedaniel.dogcam.domain.entity.DogImage
import zone.thedaniel.dogcam.domain.service.DogImageRetrievalService
import zone.thedaniel.dogcam.domain.service.ImageNotificationService
import zone.thedaniel.dogcam.domain.service.NotificationUrl

@RunWith(JUnitPlatform::class)
object SlackHooksSpek: Spek({

    givenRoutes(TestSlackApi.slackHooks(), "Slack Web Hook") {

        describe("valid request") {

            it("should return 200") {
                given()
                        .param("response_url", "http://example.org")
                        .post("/dog")
                        .then()
                        .statusCode(200)
            }
        }

        describe("invalid request") {
            it("should return 400") {
                post("/dog")
                        .then()
                        .statusCode(400)
            }
        }

    }
})


object TestSlackApi: SlackApi {
    override val dogRetrievalService: DogImageRetrievalService = TestRetrievalService
    override val notificationService: ImageNotificationService = TestNotificationService
}

object TestRetrievalService: DogImageRetrievalService {
    suspend override fun requestImage() = Try.pure(DogImage("test.jpg"))
}

object TestNotificationService: ImageNotificationService {
    suspend override fun notify(image: DogImage, notifyUrl: NotificationUrl) = Try.pure(Unit)
}
