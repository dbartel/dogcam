package zone.thedaniel.dogcam.picture_fetcher

import io.grpc.ServerBuilder
import zone.thedaniel.dogcam.domain.ext.info
import zone.thedaniel.dogcam.domain.service.DogImageRetrievalService
import zone.thedaniel.dogcam.infrastructure.service.DogRetrofitService
import zone.thedaniel.dogcam.picture_fetcher.config.loadConfig
import zone.thedaniel.dogcam.picture_fetcher.service.DogFetchService


fun main(args: Array<String>) {
    val conf = (args.getOrNull(0) ?: "application.conf").loadConfig()

    "Starting grpc on ${conf.grpc.port}".info()

    val app = object : ServerApplication {
        override val port: Int = conf.grpc.port
        override val dogService: DogImageRetrievalService = DogRetrofitService()
    }

    // Boot Grpc Server
    app.server()
            .start()
            .awaitTermination()

}

interface ServerApplication {
    val port: Int

    val dogService: DogImageRetrievalService

    fun server() =
        ServerBuilder.forPort(port)
                .addService(DogFetchService(dogService))
                .build()

}