package com.mortex.farhadmarket.data.source.remote

import com.mortex.farhadmarket.data.model.PriceItem
import retrofit2.Response
import retrofit2.http.GET

interface SwapApi {
    @GET("prices")
    suspend fun getPrices(): Response<List<PriceItem>>

}