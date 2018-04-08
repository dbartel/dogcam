package zone.thedaniel.dogcam.infrastructure.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url
import zone.thedaniel.dogcam.domain.entity.DogImage


internal interface SlackResponseApi {

    @POST
    fun respond(@Body body: SlackMessage, @Url uri: String): Deferred<ResponseBody>

    companion object {

        val singleton by lazy {
            Retrofit.Builder()
                    .baseUrl("https://hooks.slack.com/commands/")
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
                    .build()
                    .create(SlackResponseApi::class.java)
        }
    }
}



internal data class Attachment(val text: String, val image_url: String)
internal data class SlackMessage(val response_type: String = "in_channel", val text: String, val attachments: List<Attachment>)

internal fun DogImage.toSlackMessage() = SlackMessage(
        text = "Here's your dog!",
        attachments = listOf(
                Attachment("Dog!", url)
        )
)