package zone.thedaniel.dogcam.infrastructure.api

import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import zone.thedaniel.dogcam.domain.entity.DogImage

@RunWith(JUnitPlatform::class)
object DogImageExtSpek: Spek({
    lateinit var image: DogImage
    val dogImage = "http://example.org"

    beforeGroup {
        image = DogImage(dogImage)
    }

    describe("toSlackMessage") {
        lateinit var message: SlackMessage

        beforeGroup {
            message = image.toSlackMessage()
        }

        it("should say \"Here's your dog!\"") {
            message.text shouldEqual "Here's your dog!"
        }

        it("Should have a dog attachment") {
            message.attachments shouldContain Attachment("Dog!", dogImage)
        }
    }
})
