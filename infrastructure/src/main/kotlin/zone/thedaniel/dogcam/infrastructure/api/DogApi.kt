package zone.thedaniel.dogcam.infrastructure.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET

internal interface DogApi {

    @GET("breeds/image/random")
    fun getImage(): Deferred<DogImageResponse>

    companion object {
        val singleton by lazy {
            Retrofit.Builder()
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
                    .baseUrl("https://dog.ceo/api/")
                    .build()
                    .create(DogApi::class.java)
        }
    }
}

typealias DogImageUrl = String
data class DogImageResponse(val status: String, val message: DogImageUrl)