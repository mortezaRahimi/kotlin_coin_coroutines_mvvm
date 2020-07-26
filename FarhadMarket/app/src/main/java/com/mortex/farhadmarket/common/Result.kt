package com.mortex.farhadmarket.common

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class GenericError(val code: Int? = null, val internalCode: Int, val message: String?) : Result<Nothing>()
    object NetworkError : Result<Nothing>()

}