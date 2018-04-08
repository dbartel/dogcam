package zone.thedaniel.dogcam.picture_fetcher

import io.grpc.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.Spec
import org.jetbrains.spek.api.dsl.SpecBody
import org.jetbrains.spek.api.dsl.describe

/**
 * Test Harness for setting up and tearing down a gRPC
 * @param service The gRPC service under test
 * @param description String describing the service under test
 * @param body A test block. The block will be passed a channel connected to the spun up server
 */
fun Spec.givenGrpcServer(service: BindableService, description: String, body: SpecBody.(channel: ManagedChannel) -> Unit) {

    var server: Server? = null

    val channel: ManagedChannel by lazy {
        ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext(true)
                .build()
    }

    beforeGroup {
        val builder = ServerBuilder.forPort(8080)
                .addService(service)
        server = builder.build()
        server?.start()
    }

    afterGroup {
        channel.shutdownNow()
        server?.shutdownNow()
    }

    describe(description) {
        body(channel)
    }
}