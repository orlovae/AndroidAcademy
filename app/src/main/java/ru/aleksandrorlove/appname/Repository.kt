package ru.aleksandrorlove.appname

import android.util.Log
import ru.aleksandrorlove.appname.Entity.ActorEntity
import ru.aleksandrorlove.appname.Entity.GenreEntity
import ru.aleksandrorlove.appname.Entity.MovieEntity
import ru.aleksandrorlove.appname.Repository.ImageType.*
import ru.aleksandrorlove.appname.network.*
import kotlin.math.roundToInt

class Repository(private val api: TmdbApi) {
    var moviesEntity: List<MovieEntity> = emptyList()

    private var configurationImages: Images? = null

    fun getMovieEntity(id: Int): MovieEntity {
        for (item in moviesEntity) {
            if (item.id == id) {
                return item
            }
        }
        return MovieEntity(0, "", "", "", "", 0, 0, 0, 0, emptyList(), emptyList())
    }

    suspend fun init() {
        val moviesPopular: List<MoviePopular>? = getMoviesPopularOrEmptyList()
        val genres: List<GenreNetwork>? = getGenresOrEmptyList()
        if (genres != null && moviesPopular != null) {
            moviesEntity = getMoviesEntity(genres, moviesPopular)
        }
    }

    //TODO может быть стоит в аргументы метода передавать язык, поэксперементировать с результатами
    suspend fun getMoviesPopularOrEmptyList(): List<MoviePopular>? {
        val response = api.getMoviesPopular()
        return if (response.isSuccessful) {
            response.body()?.moviesPopular
        } else {
            Log.d("Repository", "ERROR - " + response.errorBody())
            emptyList()
        }
    }

    suspend fun getGenresOrEmptyList(): List<GenreNetwork>? {
        val response = api.getGenres()
        return if (response.isSuccessful) {
            response.body()?.genres
        } else {
            Log.d("Repository", "ERROR - " + response.errorBody())
            emptyList()
        }
    }

    suspend fun getMovieDetailsRatingRuntimeNetwork(movieId: Int): MovieDetailsRatingRuntimeNetwork? {
        val response = api.getMovieDetailsRatingRuntimeNetwork(movieId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            Log.d("Repository", "ERROR - " + response.errorBody())
            MovieDetailsRatingRuntimeNetwork(0, 1.0, 0)
        }
    }

    suspend fun getActorsEntityFirstSixItemOrEmptyList(movieId: Int): List<ActorEntity>? {
        val response = api.getActorsNetwork(movieId)
        return if (response.isSuccessful) {
            response.body()?.actors?.let { getActorsNetworkFirstSixItem(it) }
        } else {
            Log.d("Repository", "ERROR - " + response.errorBody())
            emptyList()
        }
    }

    suspend fun getConfiguration(): Images? {
        val response = api.getConfiguration()
        if (response.isSuccessful) {
            configurationImages = response.body()?.images
        }
        return if (response.isSuccessful) {
            configurationImages
        } else {
            Log.d("Repository", "ERROR - " + response.errorBody())
            return null
        }
    }

    suspend fun getActorsNetworkFirstSixItem(actors: List<ActorNetwork>): List<ActorEntity> {
        val sizeActorEntity: Int = if (actors.size < 6) {
            actors.size
        } else {
            7
        }
        return castActorNetworkToActorEntity(actors).subList(0, sizeActorEntity)
    }

    suspend fun castActorNetworkToActorEntity(actorsNetwork: List<ActorNetwork>): List<ActorEntity> {
        return actorsNetwork.map { actorNetwork ->
            ActorEntity(
                id = actorNetwork.id,
                name = actorNetwork.name,
                picture = createUrlImage(ACTOR, actorNetwork.picturePath)
            )
        }
    }

    fun castGenresNetworkToGenresEntity(genresNetwork: List<GenreNetwork>): List<GenreEntity> {
        return genresNetwork.map { genreNetwork ->
            GenreEntity(
                id = genreNetwork.id,
                name = genreNetwork.name
            )
        }
    }

    //TODO возвраст не раелизован
    suspend fun getMoviesEntity(
        genresNetwork: List<GenreNetwork>,
        moviesPopular: List<MoviePopular>
    ): List<MovieEntity> {
        val genresEntity = castGenresNetworkToGenresEntity(genresNetwork)
        val genresMap = genresEntity.associateBy { it.id }

        return moviesPopular.map { moviePopular ->
            MovieEntity(
                id = moviePopular.id,
                title = moviePopular.title,
                overview = moviePopular.overview,
                poster = createUrlImage(POSTER, moviePopular.poster),
                backdrop = createUrlImage(BACKDROP, moviePopular.backdrop),
                ratings = (getMovieDetailsRatingRuntimeNetwork(moviePopular.id)?.ratings)?.toInt()
                    ?: 0,
                numberOfRatings = mapperNumberPfRating(moviePopular.numberOfRatings),
                minimumAge = 13,
//                if (moviesPopular.adult) 16 else 13,
                runtime = getMovieDetailsRatingRuntimeNetwork(moviePopular.id)?.runtime ?: 0,
                genres = moviePopular.genreIDS.map {
                    genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                },
                actors = getActorsEntityFirstSixItemOrEmptyList(moviePopular.id)
                    ?: emptyList<ActorEntity>()
            )
        }
    }

    private fun mapperNumberPfRating(rating: Double): Int {
        return (rating * 0.5).roundToInt()
    }

    private suspend fun createUrlImage(imageType: ImageType, path: String?): String {
        if (configurationImages == null) {
            getConfiguration()
        }
        return when (imageType) {
            POSTER -> configurationImages?.baseUrlImages +
                    configurationImages?.posterSizes?.get(4) + path
            BACKDROP -> configurationImages?.baseUrlImages +
                    configurationImages?.backdropSizes?.get(1) + path
            ACTOR -> configurationImages?.baseUrlImages +
                    configurationImages?.posterSizes?.get(0) + path
            else -> "Заглушка для null"
        }
    }

    enum class ImageType {
        POSTER,
        BACKDROP,
        ACTOR
    }

    object Singleton {
        val instance = Repository(RetrofitModule.tmdbApi)
    }
}