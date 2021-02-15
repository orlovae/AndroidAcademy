package ru.aleksandrorlove.appname.config

object NetworkConfig {
    const val BASE_URL = "https://api.themoviedb.org/3/"

    const val ERROR_POSTER = "Заглушка для poster - null"
    const val ERROR_BACKDROP = "Заглушка для backdrop -null"
    const val ERROR_ACTOR = "Заглушка для actor - null"

    const val LANGUAGE_FOR_PG = "US"

    const val ERROR_PG = "PG_IS_NULL"

    //https://developers.themoviedb.org/3/configuration/get-api-configuration
    private val sizeLogo = arrayOf(
    "w45",
    "w92",
    "w154",
    "w185",
    "w300",
    "w500",
    "original"
    )
    private val sizeBackdrop = arrayOf(
    "w92",
    "w154",
    "w185",
    "w342",
    "w500",
    "w780",
    "original"
    )
    private val sizeProfile = arrayOf(
    "w45",
    "w185",
    "h632",
    "original"
    )
    const val POSTER_SIZE = 4
    const val BACKDROP_SIZE = 1
    const val PROFILE_SIZE = 1
}