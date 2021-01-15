package ru.aleksandrorlove.appname

import android.util.Log
import ru.aleksandrorlove.appname.data.JsonLoad
import ru.aleksandrorlove.appname.data.Movie
import ru.aleksandrorlove.appname.network.BaseRepository
import ru.aleksandrorlove.appname.network.MoviePopular
import ru.aleksandrorlove.appname.network.RetrofitModule
import ru.aleksandrorlove.appname.network.TmdbApi

class Repository(private val api : TmdbApi) : BaseRepository() {
    private var movies: List<Movie> = emptyList()

    suspend fun getListMovies(): List<Movie> {
//        getResponseActors(550)
//        getResponseGenres()
//        getMoviesPopular()
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
    suspend fun getMoviesPopular() : List<MoviePopular>? {
        val response = api.getMoviesPopular()
        return if (response.isSuccessful) {
            response.body()?.moviesPopular
        } else {
            Log.d("Repository - fun getMoviesPopular()", "ERORR - " + response.errorBody())
            emptyList()
        }
    }
//    suspend fun getResponseGenres() {
//        scope.launch {
//            val responseGenres = RetrofitModule
//                .getTmdbApi()
//                .getResponseGenres(BuildConfig.TMDB_API_KEY, "en-US")
//            val genres: List<Genre> = responseGenres.genres
//
//        }
//    }
//
//    suspend fun getResponseActors(movieId: Int) {
//        scope.launch {
//            val responseActors = RetrofitModule
//                .getTmdbApi()
//                .getResponseActors(movieId, BuildConfig.TMDB_API_KEY, "en-US")
//            val actors: List<Actor> = responseActors.actors
//
//        }
//    }

//    fun getMoviesEntity(genres: List<Genre>, moviesPopular: List<MoviePopular>) : List<ru.aleksandrorlove.appname.Entity.Movie> {
//        val genresMap = genres.associateBy { it.id }
//
//        return moviesPopular.map {moviePopular ->
//            ru.aleksandrorlove.appname.Entity.Movie(
//                id = moviePopular.id,
//                title = moviePopular.title,
//                overview = moviePopular.overview,
//                poster = moviePopular.poster,
//                backdrop = moviePopular.backdrop,
//                ratings = moviePopular.,
//                numberOfRatings = moviePopular.numberOfRatings, //TODO перенести функцию по пересчёту рейтинга
//                minimumAge = if (moviesPopular.adult) 16 else 13,
//                runtime = moviePopular.,
//                genres = moviePopular.genreIDS.map {
//                    genresMap[it] ?: throw IllegalArgumentException("Genre not found")
//                },
//            )
//        }
//    }

    object Singleton {
        val instance = Repository(RetrofitModule.tmdbApi)
    }
}