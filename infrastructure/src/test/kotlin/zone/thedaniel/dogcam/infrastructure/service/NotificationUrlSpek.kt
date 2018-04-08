package zone.thedaniel.dogcam.infrastructure.service

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it


object NotificationUrlSpek: Spek({
    describe("getEndpoint()") {
        describe("well formed link") {
            val url = "https://hooks.slack.com/commands/1234/5678"
            it("should get the link") {
                val expectedResult = "1234/5678"
                url.getEndpoint() shouldEqual expectedResult
            }
        }
    }
})