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
        return MovieEntity(0, "", "", "", "", 0, 0, "", 0, emptyList(), emptyList())
    }

    suspend fun init() {
        val moviesPopular: List<MoviePopular>? = getMoviesPopularOrEmptyList()
        val genres: List<GenreNetwork>? = getGenresOrEmptyList()
        if (genres != null && moviesPopular != null) {
            moviesEntity = getMoviesEntity(
                genres, moviesPopular,
                getListMoviesDetailsNetwork(moviesPopular)
            )
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

    suspend fun getMovieDetailNetwork(movieId: Int)
            : MovieDetailsNetwork? {
        val response = api
            .getMovieDetailsRatingRuntimeNetwork(movieId)

        return if (response.isSuccessful) {
            response.body()
        } else {
            Log.d("Repository", "ERROR - " + response.errorBody())
            MovieDetailsNetwork(0, 0.0, 0, ReleaseDates(emptyList()))
        }
    }

    suspend fun getListMoviesDetailsNetwork(moviesPopular: List<MoviePopular>):
            List<MovieDetailsNetwork> {
        val listMovieDetailsNetwork:
                MutableList<MovieDetailsNetwork> = mutableListOf()
        for (item in moviesPopular.indices) {
            getMovieDetailNetwork(moviesPopular[item].id)?.let {
                listMovieDetailsNetwork.add(
                    it
                )
            }
        }
        return listMovieDetailsNetwork
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

    suspend fun getMoviesEntity(
        genresNetwork: List<GenreNetwork>,
        moviesPopular: List<MoviePopular>,
        moviesDetails:
        List<MovieDetailsNetwork>
    ): List<MovieEntity> {
        val genresEntity = castGenresNetworkToGenresEntity(genresNetwork)
        val genresMap = genresEntity.associateBy { it.id }
        val moviesDetailsNetworkMap =
            moviesDetails.associateBy { it.id }

        return moviesPopular.map { moviePopular ->
            MovieEntity(
                id = moviePopular.id,
                title = moviePopular.title,
                overview = moviePopular.overview,
                poster = createUrlImage(POSTER, moviePopular.poster),
                backdrop = createUrlImage(BACKDROP, moviePopular.backdrop),
                ratings = moviesDetailsNetworkMap[moviePopular.id]?.ratings?.toInt() ?: 0,

                numberOfRatings = mapperNumberPfRating(moviePopular.numberOfRatings),
                minimumAge = getMinimumAge(moviesDetails),

                runtime = moviesDetailsNetworkMap[moviePopular.id]?.runtime ?: 0,

                genres = moviePopular.genreIDS.map {
                    genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                },
                actors = getActorsEntityFirstSixItemOrEmptyList(moviePopular.id)
                    ?: emptyList<ActorEntity>()
            )
        }
    }

    //TODO В данной функции можно добавить агрумент language: String для выбора нащиональной системы рейтинга
    private fun getMinimumAge(moviesDetails: List<MovieDetailsNetwork>): String {
        for (index in moviesDetails.indices) {
            val result = moviesDetails[index].releaseDates.results

            for (i in result.indices) {
                if (result[i].iso_3166_1.equals("RU", true)) {
                    Log.d(
                        "getMinimumAge",
                        "minimumAge - " + result[i].release_dates_result_item[0].certification.toString()
                    )
                    return result[i].release_dates_result_item[0].certification.toString()
                }
            }
        }
        return "0"
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