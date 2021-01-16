package ru.aleksandrorlove.appname.network

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
    ): Response<MoviesPopular>

    @GET("genre/movie/list?")
    suspend fun getGenres(
        @Query("api_key") api_key: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US"
    ): Response<Genres>

    @GET("movie/{movie_id}/credits?")
    suspend fun getActors(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String  = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String  = "en-US"
    ): Response<Actors>

    @GET("configuration")
    suspend fun getConfiguration(
        @Query("api_key") api_key: String  = BuildConfig.TMDB_API_KEY
    ): Response<Configuration>
}