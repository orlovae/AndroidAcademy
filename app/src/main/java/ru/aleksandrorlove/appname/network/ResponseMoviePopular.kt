package ru.aleksandrorlove.appname.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMoviePopular(
    @SerialName("results")
    val moviesPopular: List<MoviePopular>
)

@Serializable
data class MoviePopular(
    @SerialName("backdrop_path")
    val backdrop: String? = null,

    @SerialName("genre_ids")
    val genreIDS: List<Long>,

    @SerialName("id")
    val id: Long,

    @SerialName("original_title")
    val title: String,

    @SerialName("overview")
    val overview: String,

    @SerialName("poster_path")
    val poster: String? = null,

    @SerialName("vote_average")
    val numberOfRatings: Double
)
