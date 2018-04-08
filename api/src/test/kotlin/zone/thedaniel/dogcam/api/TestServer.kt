package zone.thedaniel.dogcam.api

import org.http4k.core.HttpHandler
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.jetbrains.spek.api.dsl.Spec
import org.jetbrains.spek.api.dsl.SpecBody
import org.jetbrains.spek.api.dsl.describe


fun Spec.givenRoutes(routes: HttpHandler, description: String, body: SpecBody.() -> Unit) {
    var server: Http4kServer? = null

    beforeGroup {
        server = routes.asServer(Jetty(8080))
        server?.start()
    }

    afterGroup {
        server?.stop()
    }

    describe(description, body)
}


