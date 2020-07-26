package com.mortex.farhadmarket.data.source.remote

import com.mortex.farhadmarket.common.Api
import com.mortex.farhadmarket.common.Result
import com.mortex.farhadmarket.data.model.PriceItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SwapRemoteDataSrc(
    private val swapApi: SwapApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SwapDataSrc {
    override suspend fun getPrices(): Result<List<PriceItem>> {
        return withContext(dispatcher) {
            return@withContext Api.result {
                swapApi.getPrices()
            }
        }
    }

}