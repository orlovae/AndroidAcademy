package ru.aleksandrorlove.appname.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.aleksandrorlove.appname.BuildConfig


object RetrofiModule {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val contentType = "application/json".toMediaType()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun createRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(httpClient)
        .build()
}