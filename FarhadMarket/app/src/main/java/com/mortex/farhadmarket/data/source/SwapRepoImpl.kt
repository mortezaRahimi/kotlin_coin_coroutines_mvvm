package com.mortex.farhadmarket.data.source

import com.mortex.farhadmarket.common.Result
import com.mortex.farhadmarket.data.model.PriceItem
import com.mortex.farhadmarket.data.source.remote.SwapDataSrc

class SwapRepoImpl(private val swapDataSrc: SwapDataSrc):SwapRepo {

    override suspend fun getPrices(): Result<List<PriceItem>> {
        return swapDataSrc.getPrices()
    }
}