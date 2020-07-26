package com.mortex.farhadmarket.di

import com.mortex.farhadmarket.common.Api
import com.mortex.farhadmarket.data.source.SwapRepo
import com.mortex.farhadmarket.data.source.SwapRepoImpl
import com.mortex.farhadmarket.data.source.remote.SwapApi
import com.mortex.farhadmarket.data.source.remote.SwapDataSrc
import com.mortex.farhadmarket.data.source.remote.SwapRemoteDataSrc
import com.mortex.farhadmarket.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val swapModule = module {
    viewModel { MainViewModel(get()) }

    factory {
        provideSwapApi(
            get(
                named("withWrapper"),
                parameters = { parametersOf(Api.HOST) }
            )
        )
    }

    factory<SwapDataSrc> { SwapRemoteDataSrc(get()) }
    factory<SwapRepo> {
        SwapRepoImpl(get())
    }
}


fun provideSwapApi(retrofit: Retrofit): SwapApi = retrofit.create(
    SwapApi::class.java
)