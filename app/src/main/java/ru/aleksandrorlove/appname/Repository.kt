package ru.aleksandrorlove.appname

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.aleksandrorlove.appname.data.JsonLoad
import ru.aleksandrorlove.appname.data.Movie
import ru.aleksandrorlove.appname.network.MoviesPopularApi
import ru.aleksandrorlove.appname.network.RetrofitClient

class Repository {
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var movies: List<Movie> = emptyList()

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
    suspend fun getListMoviesPopular() {
        scope.launch {
            val moviesPopular = RetrofitClient.createRetrofit().create(MoviesPopularApi::class.java)
                .getMoviesPopular(BuildConfig.API_KEY, "en-US")
        }
    }

    object Singleton {
        val instance = Repository()
    }
}