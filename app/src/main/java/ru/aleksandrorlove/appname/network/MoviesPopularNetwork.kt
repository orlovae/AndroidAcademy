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
    @SerialName("id")
    val id: Int
)
