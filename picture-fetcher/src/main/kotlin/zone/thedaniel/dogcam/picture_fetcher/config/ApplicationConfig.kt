package zone.thedaniel.dogcam.picture_fetcher.config


import com.typesafe.config.ConfigFactory
import io.github.config4k.extract

data class ApplicationConfig(val grpc: GrpcConfig)

data class GrpcConfig(val port: Int)

fun String.loadConfig() = ConfigFactory.load(this).run {
    extract<ApplicationConfig>("app")
}