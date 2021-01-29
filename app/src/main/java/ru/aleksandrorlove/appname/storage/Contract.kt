package ru.aleksandrorlove.appname.storage

object Contract {
    const val DATABASE_NAME = "movies.db"

    object Movie {
        const val TABLE_NAME = "Movies"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_POSTER = "poster"
        const val COLUMN_NAME_BACKDROP = "backdrop"
        const val COLUMN_NAME_RATINGS = "ratings"
        const val COLUMN_NAME_NUMBER_OF_RAITING = "numberOfRatings"
        const val COLUMN_NAME_MINIMUM_AGE = "minimumAge"
        const val COLUMN_NAME_RUNTIME = "runtime"
        const val COLUMN_NAME_GENRE_ID = "genreId"
        const val COLUMN_NAME_GENRE_NAME = "genreName"
        const val COLUMN_NAME_ACTOR_ID = "actorId"
        const val COLUMN_NAME_ACTOR_NAME = "actorNome"
        const val COLUMN_NAME_ACTOR_PICTURE = "actorPicture"
    }
}