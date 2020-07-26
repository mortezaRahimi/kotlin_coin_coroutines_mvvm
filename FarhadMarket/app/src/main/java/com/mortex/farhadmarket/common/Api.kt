package com.mortex.farhadmarket.common

import android.util.Log
import com.google.gson.Gson
import com.mortex.farhadmarket.BuildConfig
import retrofit2.Response

object Api {

    val HOST get() = BuildConfig.BASE_URL_API
    private const val KEY_BEARER = "Bearer "

    inline fun <reified T : Any> result(service: () -> Response<T>): Result<T> {
        return try {

            val result = service()
            if (result.isSuccessful) {
                Result.Success(result.body()!!)
            } else {
                val gSon = Gson()
                val baseApiError = gSon.fromJson(result.errorBody()?.string(), InternalErrorResponse::class.java)
                Result.GenericError(result.code(), baseApiError.code, baseApiError.errorMessage)
            }
        } catch (e: Exception) {
            Log.e("Network Errors", e.message, e)
            Result.NetworkError
        }
    }

    inline fun <reified T : Any, P> result(filter: ((Response<P>) -> T), service: () -> Response<P>): Result<T> {
        return try {
            val result = service()
            if (result.isSuccessful) {
                Result.Success(filter(result))
            } else {
                val gSon = Gson()
                val baseApiError = gSon.fromJson(result.errorBody()?.string(), InternalErrorResponse::class.java)
                Result.GenericError(result.code(), baseApiError.code, baseApiError.errorMessage)
            }
        } catch (e: Exception) {
            Result.NetworkError
        }
    }

    fun token(jwt: String): String {
        return KEY_BEARER + jwt
    }

}