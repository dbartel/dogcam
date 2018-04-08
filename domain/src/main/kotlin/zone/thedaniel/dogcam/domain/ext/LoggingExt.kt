package zone.thedaniel.dogcam.domain.ext

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun logger(name: String) = LoggerFactory.getLogger(name)

internal val DEFAULT_LOGGER = logger("DOGCAM")

fun String.error(log: Logger = DEFAULT_LOGGER) {
    log.error(this)
}

fun String.info(log: Logger = DEFAULT_LOGGER) {
    log.info(this)
}

