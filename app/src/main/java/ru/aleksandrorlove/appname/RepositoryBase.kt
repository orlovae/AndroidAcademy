package ru.aleksandrorlove.appname

import android.util.Log
import retrofit2.Response
import ru.aleksandrorlove.appname.network.Result
import java.io.IOException

open class RepositoryBase {

//    suspend fun <T : Any> safeApiCall(
//        call: suspend () -> Response<T>,
//        errorMessage: String
//    ): T? {
//        val result: Result<T> = safeApiResult(call, errorMessage)
//        var data: T? = null
//
//        when (result) {
//            is Result.Success ->
//                data = result.data
//            is Result.Error -> {
//                Log.d("Repository", "$errorMessage & Exception - ${result.exception}")
//            }
//        }
//        return data
//    }

    suspend fun <T : Any> getDataResponse(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): T? {
        val result: Result<T> = safeApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is Result.Success ->
                data = result.data
            is Result.Error -> {
                Log.d("Repository", "$errorMessage & Exception - ${result.message}")
            }
        }
        return data
    }

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Result<T> {
        val result: Result<T> = safeApiResult(call, errorMessage)
        return result
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Result<T> {
        val response = call.invoke()
        if (response.isSuccessful) return Result.Success(response.body()!!)
//TODO прописать возможные варианты когад респонс суксесс, но пришледшее боди - говно
        return Result.Error(
            IOException(
                "Error Occurred during getting safe Api result," +
                        " Custom ERROR - $errorMessage"
            ).toString()
        )
    }
}