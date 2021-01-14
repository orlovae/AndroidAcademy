package ru.aleksandrorlove.appname

import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.aleksandrorlove.appname.data.JsonLoad
import ru.aleksandrorlove.appname.data.Movie
import ru.aleksandrorlove.appname.network.*

class Repository {
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var movies: List<Movie> = emptyList()

    suspend fun getListMovies(): List<Movie> {
        getResponseActors(550)
        getResponseGenres()
        getResponseMoviesPopular()
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
    suspend fun getResponseMoviesPopular() {
        scope.launch {
            val responseMoviePopular = RetrofiModule
                .createRetrofit()
                .create(TmdbApi::class.java)
                .getResponseMoviesPopular(BuildConfig.TMDB_API_KEY, "en-US")
            val moviesPopular: List<MoviePopular> = responseMoviePopular.moviesPopular
            for (item in moviesPopular) {
//                Log.d("Movie", "movie is" + item.toString())
            }
        }
    }

    suspend fun getResponseGenres() {
        scope.launch {
            val responseGenres = RetrofiModule
                .createRetrofit()
                .create(TmdbApi::class.java)
                .getResponseGenres(BuildConfig.TMDB_API_KEY, "en-US")
            val genres: List<Genre> = responseGenres.genres
            for (item in genres) {
//                Log.d("Movie", "genre is " + item.toString())
            }
        }
    }

    suspend fun getResponseActors(movieId: Int) {
        scope.launch {
            val responseActors = RetrofiModule
                .createRetrofit()
                .create(TmdbApi::class.java)
                .getResponseActors(movieId, BuildConfig.TMDB_API_KEY, "en-US")
            val actors: List<Actor> = responseActors.actors
            for (item in actors) {
                Log.d("Movie", "actor is " + item.toString())
            }
        }
    }

    object Singleton {
        val instance = Repository()
    }
}