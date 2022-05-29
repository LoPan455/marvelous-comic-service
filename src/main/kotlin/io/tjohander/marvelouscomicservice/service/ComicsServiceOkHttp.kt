package io.tjohander.marvelouscomicservice.service

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.tjohander.marvelouscomicservice.MarvelApiUrls
import io.tjohander.marvelouscomicservice.MarvelQueryParam
import io.tjohander.marvelouscomicservice.model.api.Comic
import io.tjohander.marvelouscomicservice.model.api.DataWrapper
import io.tjohander.marvelouscomicservice.repository.ComicRepository
import io.tjohander.marvelouscomicservice.util.MarvelAuthGenerator
import io.tjohander.marvelouscomicservice.util.SkipBadListObjectsAdapterFactory
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.io.IOException
import java.time.Instant
import java.util.*

@Service
@Primary
class ComicsServiceOkHttp(
    private val client: OkHttpClient,
    private val repository: ComicRepository,
    @Value("\${marvel-api.public-key}") val marvelApiPublicKey: String,
    @Value("\${marvel-api.private-key}") val marvelApiPrivateKey: String,
) : IComicService {

    override fun getComicById(id: String): Comic? {
        val auth = MarvelAuthGenerator.buildAuthString(
            Instant.now(),
            marvelApiPublicKey,
            marvelApiPrivateKey
        )
        val url = HttpUrl.Builder()
            .scheme(MarvelApiUrls.SCHEME.value)
            .host(MarvelApiUrls.HOST.value)
            .addPathSegment(MarvelApiUrls.API_VERSION.value)
            .addPathSegment(MarvelApiUrls.PUBLIC.value)
            .addPathSegment(MarvelApiUrls.COMICS_PATH.value)
            .addPathSegment(id)
            .addQueryParameter(MarvelQueryParam.TIMESTAMP.value, auth.ts)
            .addQueryParameter(MarvelQueryParam.API_KEY.value, auth.publicKey)
            .addQueryParameter(MarvelQueryParam.HASH.value, auth.md5Hash)
            .build()
        val request: Request = Request.Builder()
            .url(url)
            .headers(Headers.headersOf("Content-Type", "application/json"))
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Error!: $response")
            println(response.headers.toMultimap().entries)
            val moshi = Moshi.Builder()
                .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
                .add(SkipBadListObjectsAdapterFactory())
                .addLast(KotlinJsonAdapterFactory()).build()
            val type = Types.newParameterizedType(DataWrapper::class.java, Comic::class.java, Comic::class.java)
            val jsonAdapter: JsonAdapter<DataWrapper<Comic>> = moshi.adapter<DataWrapper<Comic>?>(type).lenient()
            val comic: Comic? = jsonAdapter.nullSafe().fromJson(response.body!!.source())?.data?.results?.firstOrNull()
            comic?.let { repository.save(it) }
            return comic
        }
    }
}