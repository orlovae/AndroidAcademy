package ru.aleksandrorlove.appname.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Int,
    val reviews: Int,
    val minimumAge: String,
    val runtime: Int,
    val genres: List<Genre>,
    val actors: List<Actor>
) {
    constructor() : this(
        0,
        "",
        "",
        "",
        "",
        0,
        0,
        "",
        0,
        emptyList(),
        emptyList()
    )
}