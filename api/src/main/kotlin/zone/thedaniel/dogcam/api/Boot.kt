package zone.thedaniel.dogcam.api

import io.grpc.ManagedChannelBuilder
import org.http4k.server.Jetty
import org.http4k.server.asServer
import zone.thedaniel.dogcam.DogPhotoServiceGrpc
import zone.thedaniel.dogcam.api.config.loadConfig
import zone.thedaniel.dogcam.api.route.Api
import zone.thedaniel.dogcam.api.route.routes
import zone.thedaniel.dogcam.domain.service.DogImageRetrievalService
import zone.thedaniel.dogcam.domain.service.ImageNotificationService
import zone.thedaniel.dogcam.infrastructure.service.DogGrpcService
import zone.thedaniel.dogcam.infrastructure.service.NotificationRetrofitService


fun main(args: Array<String>) {

    // Load Conf
    val conf = (args.getOrNull(0) ?: "application.conf").loadConfig()

    // Configure gRPC
    val channel = ManagedChannelBuilder.forAddress(conf.grpc.host, conf.grpc.port)
            .usePlaintext(true)
            .build()

    // Configure Routes
    val app = (object: Api {
        override val isDebug = conf.debug

        override val applicationVersion: String = conf.version

        override val dogRetrievalService: DogImageRetrievalService = DogGrpcService(DogPhotoServiceGrpc.newStub(channel))

        override val notificationService: ImageNotificationService = NotificationRetrofitService()

    }).routes()

    // Start
    app.asServer(Jetty(conf.http.port)).start().block()
}

