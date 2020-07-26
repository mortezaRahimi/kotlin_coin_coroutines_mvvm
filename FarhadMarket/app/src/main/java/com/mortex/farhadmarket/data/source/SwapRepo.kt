package com.mortex.farhadmarket.data.source

import com.mortex.farhadmarket.common.Result
import com.mortex.farhadmarket.data.model.PriceItem

interface SwapRepo {

    suspend fun getPrices(): Result<List<PriceItem>>
}