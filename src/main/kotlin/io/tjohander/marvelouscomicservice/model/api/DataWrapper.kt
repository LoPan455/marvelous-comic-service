package io.tjohander.marvelouscomicservice.model.api


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
data class DataWrapper<Comic>(
    val code: Int,
    val status: String,
    val data: DataContainer<Comic>,
    val etag: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String
)

@JsonClass(generateAdapter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
data class DataContainer<Comic>(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Comic>
)


@JsonIgnoreProperties(ignoreUnknown = true)
data class ErrorContainer(
    val code: String,
    override val message: String
) : Throwable()
