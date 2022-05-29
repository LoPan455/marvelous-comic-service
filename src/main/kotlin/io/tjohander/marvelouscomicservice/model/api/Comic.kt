package io.tjohander.marvelouscomicservice.model.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import org.springframework.data.annotation.Id
import java.time.LocalDate
import java.util.*

@JsonClass(generateAdapter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Comic(
    @Id val id: Int,
    val digitalId: Int,
    val title: String,
    val issueNumber: Int,
    val variantDescription: String,
    val description: String? = null,
    val modified: Date,
    val format: String,
    val pageCount: Int,
    val textObjects: List<TextObject>,
    val resourceURI: String,
    val urls: List<URL>,
    val series: SeriesSummary,
    val variants: List<ComicSummary>,
    val collections: List<ComicSummary>,
    val collectedIssues: List<ComicSummary>,
    val dates: List<ComicDate>,
    val prices: List<ComicPrice>,
    val thumbnail: Image,
    val images: List<Image>,
    val creators: ResourceList<Creator>,
    val characters: ResourceList<Character>,
    val stories: ResourceList<Story>,
    val events: EventList
)

@JsonClass(generateAdapter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
data class SeriesSummary(
    val resourceURI: String? = null,
    val url: String? = null
)

@JsonClass(generateAdapter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ComicSummary(
    val resourceURI: String? = null,
    val url: String? = null
)

@JsonClass(generateAdapter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ComicDate(
    val type: String? = null,
    val date: Date? = null
)


@JsonClass(generateAdapter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ComicPrice(
    val type: String? = null,
    val price: Float? = null
)

@JsonClass(generateAdapter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
data class EventList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<EventSummary>
)

@JsonClass(generateAdapter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
data class EventSummary(
    val resourceURI: String,
    val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Creator(
    val id: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Story(
    val id: Int,
    val title: String,
    val description: String,
    val resourceURI: String,
    val type: String,
    val modified: Date,
    val thumbnail: Image,
    val comics: List<Comic>,
    val series: List<Series>,
    val characters: List<Character>,
    val creators: List<Creator>,
    val originalIssue: ComicSummary
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Series(
    val id: Int
)