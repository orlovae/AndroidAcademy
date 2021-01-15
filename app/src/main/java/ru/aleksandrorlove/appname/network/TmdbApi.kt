package ru.aleksandrorlove.appname.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.aleksandrorlove.appname.BuildConfig

interface TmdbApi {

    @GET("movie/popular?")
    suspend fun getMoviesPopular(
        @Query("api_key") api_key: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<ResponseMoviePopular>

    @GET("genre/movie/list?")
    fun getResponseGenres(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): ResponseGenres

    @GET("movie/{movie_id}/credits?")
    fun getResponseActors(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): ResponseActors
}