package ru.aleksandrorlove.appname.Entity

import ru.aleksandrorlove.appname.data.Genre

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int,
    val genres: List<Genre>
)