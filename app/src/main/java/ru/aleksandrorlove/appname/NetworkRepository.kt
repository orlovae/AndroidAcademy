package ru.aleksandrorlove.appname

import ru.aleksandrorlove.appname.network.*

class NetworkRepository(private val api: TmdbApi) : BaseRepository() {
    private var configurationImages: Configuration? = null

    //TODO может быть стоит в аргументы метода передавать язык, поэксперементировать с результатами
    suspend fun getListMoviePopularNetwork(): List<MoviePopularNetwork> {
        val response = safeApiCall(
            call = { api.getMoviesPopular()},
            errorMessage = "Error Fetching Popular Movies"
        )
        return response?.moviesPopular ?: emptyList()
    }

    suspend fun getListGenreNetwork(): List<GenreNetwork> {
        val response = safeApiCall(
            call = { api.getGenres() },
            errorMessage = "Error Fetching Genres"
        )
        return response?.genres ?: emptyList()
    }

    suspend fun getMovieDetailNetwork(movieId: Int): MovieDetailsNetwork? {
        return safeApiCall(
            call = { api.getMovieDetailsNetwork(movieId) },
            errorMessage = "Error Fetching Movie Detail"
        )
    }

    suspend fun getListMovieDetailsNetwork(moviesPopular: List<MoviePopularNetwork>):
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

    suspend fun getListActorsNetwork(movieId: Int): List<ActorNetwork> {
        val response = safeApiCall(
            call = { api.getActorsNetwork(movieId) },
            errorMessage = "Error Fetching Actors"
        )
        return response?.actors ?: emptyList()
    }

    suspend fun getConfiguration(): Configuration? {
        return if (configurationImages == null) {
            val response = safeApiCall(
                call = { api.getConfiguration() },
                errorMessage = "Error Fetching Configuration Api"
            )
            configurationImages = response
            response
        } else {
            configurationImages
        }
    }

    object Singleton {
        val instance = NetworkRepository(RetrofitModule.tmdbApi)
    }
}