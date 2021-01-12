package ru.aleksandrorlove.appname.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.aleksandrorlove.appname.BuildConfig
import java.util.concurrent.TimeUnit


object RetrofitClient {

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
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(httpClient)
            .build()
    }
}