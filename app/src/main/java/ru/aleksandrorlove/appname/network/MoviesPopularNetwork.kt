package ru.aleksandrorlove.appname.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesPopularNetwork(
    @SerialName("results")
    val moviesPopular: List<MoviePopularNetwork>
)

@Serializable
data class MoviePopularNetwork(
    @SerialName("backdrop_path")
    val backdrop: String? = null,

    @SerialName("genre_ids")
    val genreIDS: List<Int>,

    @SerialName("id")
    val id: Int,

    @SerialName("original_title")
    val title: String,

    @SerialName("overview")
    val overview: String,

    @SerialName("poster_path")
    val poster: String? = null,

    @SerialName("vote_average")
    val numberOfRatings: Double
)
