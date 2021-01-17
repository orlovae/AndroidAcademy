package ru.aleksandrorlove.appname

import android.util.Log
import ru.aleksandrorlove.appname.Entity.ActorEntity
import ru.aleksandrorlove.appname.Entity.MovieEntity
import ru.aleksandrorlove.appname.Repository.ImageType.*
import ru.aleksandrorlove.appname.data.JsonLoad
import ru.aleksandrorlove.appname.data.Movie
import ru.aleksandrorlove.appname.network.*
import kotlin.math.roundToInt

class Repository(private val api: TmdbApi) {
    private var movies: List<Movie> = emptyList()

    private var configurationImages: Images? = null

    suspend fun getListMovies(): List<Movie> {
        movies = JsonLoad().loadMovies()
        return movies
    }

    fun getMovie(id: Int): Movie {
        var movie: Movie? = null
        for (item in movies) {
            if (item.id == id) {
                movie = item
                break
            }
        }
        return movie!!
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

    suspend fun getGenresOrEmptyList(): List<Genre>? {
        val response = api.getGenres()
        return if (response.isSuccessful) {
            response.body()?.genres
        } else {
            Log.d("Repository", "ERROR - " + response.errorBody())
            emptyList()
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

    fun getActorsNetworkFirstSixItem(actors: List<ActorNetwork>): List<ActorEntity> {
        val sizeActorEntity : Int = if (actors.size < 6) {
            actors.size
        } else {
            7
        }
        return castActorToActorEntity(actors).subList(0, sizeActorEntity)
    }

    fun castActorToActorEntity(actorsNetwork: List<ActorNetwork>) : List<ActorEntity> {
        return actorsNetwork.map { actorNetwork ->
            ActorEntity(
                id = actorNetwork.id,
                name = actorNetwork.name,
                picture = actorNetwork.picturePath ?: "Заглушка для отсутсвующих картинок"
            )
        }
    }

    suspend fun getMoviesEntity(
        genres: List<Genre>,
        moviesPopular: List<MoviePopular>
    ): List<MovieEntity> {
        val genresMap = genres.associateBy { it.id }

        return moviesPopular.map { moviePopular ->
            MovieEntity(
                id = moviePopular.id,
                title = moviePopular.title,
                overview = moviePopular.overview,
                poster = createUrlImage(POSTER, moviePopular.poster),
                backdrop = createUrlImage(BACKDROP, moviePopular.backdrop),
                ratings = 1.0F,
                numberOfRatings = mapperNumberPfRating(moviePopular.numberOfRatings),
                minimumAge = 13,
//                if (moviesPopular.adult) 16 else 13,
                runtime = 1,
                genres = moviePopular.genreIDS.map {
                    genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                },
                actors = getActorsEntityFirstSixItemOrEmptyList(moviePopular.id) ?: emptyList<ActorEntity>()
            )
        }
    }

    private fun mapperNumberPfRating(rating: Double): Int {
        return (rating * 0.5).roundToInt()
    }

    private fun createUrlImage(imageType: ImageType, path: String?): String {
        return when (imageType) {
            POSTER -> configurationImages?.baseUrlImages +
                    configurationImages?.posterSizes?.get(4) + path
            BACKDROP -> configurationImages?.baseUrlImages +
                    configurationImages?.backdropSizes?.get(1) + path
            ACTOR -> ""
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