package ru.aleksandrorlove.appname.Entity

data class MovieEntity(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int,
    val genres: List<ru.aleksandrorlove.appname.network.Genre>,
    val actors: List<ActorEntity>
)