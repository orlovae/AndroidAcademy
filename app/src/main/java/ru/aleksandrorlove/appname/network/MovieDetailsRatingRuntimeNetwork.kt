package ru.aleksandrorlove.appname.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRatingRuntimeNetwork(
    @SerialName("id")
    val id: Int,

    @SerialName("vote_average")
    val ratings: Double,

    @SerialName("runtime")
    val runtime: Int? = null,
)