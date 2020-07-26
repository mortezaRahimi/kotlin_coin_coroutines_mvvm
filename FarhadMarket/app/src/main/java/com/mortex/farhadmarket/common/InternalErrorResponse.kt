package com.mortex.farhadmarket.common

import com.google.gson.annotations.SerializedName

data class InternalErrorResponse(
    val code: Int,
    @SerializedName("error") val errorMessage: String
)