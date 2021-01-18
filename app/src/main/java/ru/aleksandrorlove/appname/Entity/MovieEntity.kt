package ru.aleksandrorlove.appname.Entity

data class MovieEntity(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Int,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int,
    val genres: List<GenreEntity>,
    val actors: List<ActorEntity>
)