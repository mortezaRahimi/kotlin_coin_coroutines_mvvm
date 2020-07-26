package com.mortex.farhadmarket.data.source.remote

import com.mortex.farhadmarket.common.Result
import com.mortex.farhadmarket.data.model.PriceItem

interface SwapDataSrc {
    suspend fun getPrices(): Result<List<PriceItem>>
}