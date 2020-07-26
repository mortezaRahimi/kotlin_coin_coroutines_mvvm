package com.mortex.farhadmarket

import android.app.Application
import com.mortex.farhadmarket.common.networkModule
import com.mortex.farhadmarket.di.swapModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SwapApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SwapApp)
            modules(
                listOf(
                    networkModule,swapModule
                )
            )
        }
    }
}