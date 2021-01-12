package ru.aleksandrorlove.appname.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesPopularApi {

    @GET("popular?")
    suspend fun getMoviesPopular(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): List<MoviePopularResponse>
}