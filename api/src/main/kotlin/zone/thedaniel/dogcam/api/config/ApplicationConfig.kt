package zone.thedaniel.dogcam.api.config

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract

data class ApplicationConfig(val http: HttpConfig, val debug: Boolean, val version: String, val grpc: GrpcConfig)
data class HttpConfig(val port: Int)
data class GrpcConfig(val host: String, val port: Int)

fun String.loadConfig() = ConfigFactory.load(this).run {
    extract<ApplicationConfig>("app")
}