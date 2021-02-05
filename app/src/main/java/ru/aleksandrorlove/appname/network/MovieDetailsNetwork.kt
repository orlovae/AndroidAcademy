package ru.aleksandrorlove.appname.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsNetwork(
    @SerialName("id")
    val id: Int,

    @SerialName("original_title")
    val title: String,

    @SerialName("overview")
    val overview: String,

    @SerialName("poster_path")
    val poster: String? = null,

    @SerialName("backdrop_path")
    val backdrop: String? = null,

    @SerialName("vote_average")
    val ratings: Double,

    @SerialName("vote_count")
    val reviews: Int,

    @SerialName("runtime")
    val runtime: Int? = null,

    @SerialName("genres")
    val genres: List<GenreNetwork>,

    @SerialName("release_dates")
    val releaseDates: ReleaseDates
)

//@Serializable
//data class GenresItem(
//    @SerialName("id")
//    val id: Int,
//    @SerialName("name")
//    val name: String
//)

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
