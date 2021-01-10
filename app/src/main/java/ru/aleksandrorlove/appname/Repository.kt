package ru.aleksandrorlove.appname

import ru.aleksandrorlove.appname.data.JsonLoad
import ru.aleksandrorlove.appname.data.Movie

class Repository {
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

    companion object {
        val instance = Repository()
    }
}