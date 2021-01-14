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
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIDS: List<Long>? = null,

    @SerialName("id")
    val id: Long? = null,

    @SerialName("original_title")
    val original_title: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("poster_path")
    val poster: String? = null,

    @SerialName("vote_average")
    val numberOfRatings: Double? = null
)
