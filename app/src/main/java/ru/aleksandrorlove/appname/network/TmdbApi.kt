package ru.aleksandrorlove.appname.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("movie/popular?")
    suspend fun getResponseMoviesPopular(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): ResponseMoviePopular

    @GET("genre/movie/list?")
    suspend fun getResponseGenres(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): ResponseGenres

    @GET("movie/{movie_id}/credits?")
    suspend fun getResponseActors(
        @Path("movie_id")   movieId: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): ResponseActors
}