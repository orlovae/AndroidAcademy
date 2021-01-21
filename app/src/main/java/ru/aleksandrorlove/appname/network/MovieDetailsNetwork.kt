package ru.aleksandrorlove.appname.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsNetwork(
    @SerialName("id")
    val id: Int,

    @SerialName("vote_average")
    val ratings: Double,

    @SerialName("runtime")
    val runtime: Int? = null,

    @SerialName("release_dates")
    val releaseDates: ReleaseDates
)

@Serializable
data class ReleaseDates(
    @SerialName("results")
    val results: List<ResultItem>
)

@Serializable
data class ResultItem(
    @SerialName("iso_3166_1")
    val iso_3166_1: String,

    @SerialName("release_dates")
    val release_dates_result_item: List<ReleaseDate>
)

@Serializable
data class ReleaseDate(
    @SerialName("certification")
    val certification: String? = null
)
