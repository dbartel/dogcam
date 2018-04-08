package zone.thedaniel.dogcam.domain.ext

import arrow.data.Success
import arrow.data.Try


suspend fun<T, V> Try<T>.sFlatMap(block: suspend (T) -> Try<V>) = when (this) {
    is Success -> block(value)
    else -> this
}
