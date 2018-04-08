package zone.thedaniel.dogcam.api.route.diagnostic

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import io.restassured.RestAssured.get
import org.hamcrest.Matchers.*
import org.jetbrains.spek.api.dsl.describe
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import zone.thedaniel.dogcam.api.givenRoutes

@RunWith(JUnitPlatform::class)
object DiagnosticRouteSpek: Spek({
    givenRoutes(TestDiagnostics.diagnosticRoutes(), "Diagnostic Routes") {

        describe("Ping the server") {

            val call by lazy { get("/ping").then() }

            it("should return status 200") {
                call.statusCode(200)
            }

            it("should pong") {
                call.body(equalTo("pong!"))
            }
        }

        describe("Version Query") {
            val call by lazy { get("/version").then() }

            it("should return status 200") {
                call.statusCode(200)
            }

            it("should return the application version") {
                call.body(equalTo(TestDiagnostics.applicationVersion))
            }
        }
    }
})

private object TestDiagnostics: DiagnosticApi {
    override val applicationVersion = "TEST"
}