package ru.aleksandrorlove.appname

import ru.aleksandrorlove.appname.network.Configuration
import ru.aleksandrorlove.appname.network.Result
import ru.aleksandrorlove.appname.network.RetrofitModule
import ru.aleksandrorlove.appname.network.TmdbApi


class RepositoryNetwork(private val api: TmdbApi) : RepositoryBase() {
    private var resultConfiguration: Result<Configuration>? = null

    //TODO может быть стоит в аргументы метода передавать язык, поэксперементировать с результатами
    suspend fun getResultListMoviePopularNetwork(): Result<Any> {
        return safeApiCall(
            call = { api.getMoviesPopular() },
            errorMessage = "Error Fetching Popular Movies"
        )
    }

    suspend fun getResultMovieDetailNetwork(movieId: Int): Result<Any> {
        return safeApiCall(
            call = { api.getMovieDetailsNetwork(movieId) },
            errorMessage = "Error Fetching Movie Detail"
        )
    }

    suspend fun getResultListActorsNetwork(movieId: Int): Result<Any> {
        return safeApiCall(
            call = { api.getActorsNetwork(movieId) },
            errorMessage = "Error Fetching Actors"
        )
    }

    suspend fun getResultConfiguration(): Result<Configuration> {
        if (resultConfiguration == null) {
            val response = safeApiCall(
                call = { api.getConfiguration() },
                errorMessage = "Error Fetching Configuration Api"
            )
            resultConfiguration = response
        }
        return resultConfiguration as Result<Configuration>
    }

    object Singleton {
        val instance = RepositoryNetwork(RetrofitModule.tmdbApi)
    }
}