package com.mortex.farhadmarket.common


import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideGsonConverterFactory() }
    factory { provideConverterFactory() }
    factory {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
//    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get()) }
    single(named("withWrapper")) { (url: String) -> provideRetrofit(get(), get(), url) }
    single(named("withOutWrapper")) { (url: String) ->
        provideRetrofit(
            get(),
            GsonConverterFactory.create(GsonBuilder().setLenient().create()),
            url
        )
    }
    single(named("message")) { (url: String) ->
        provideRetrofit(
            get(),
            GsonConverterFactory.create(GsonBuilder().setLenient().create()),
            url
        )
    }
}


//fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()
//
//fun provideConverterFactory(): Converter.Factory =
//    GsonConverterFactory.create()

fun provideGsonConverterFactory(): GsonConverterFactory {
    val gson = GsonBuilder()
        .setLenient()
        .create()
    return GsonConverterFactory.create(gson)
}

fun provideConverterFactory(): Converter.Factory =
    GsonConverterFactory.create(GsonBuilder().setLenient().create())


fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
    OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()


fun provideRetrofit(
    okHttpClient: OkHttpClient,
    converterFactory: Converter.Factory,
    url: String
): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(url)
        .addConverterFactory(converterFactory)
        .build()

//class AuthInterceptor : Interceptor, KoinComponent {
//
//    val auth: AuthLocalDataSource by inject()
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val user: User = get()
//        val newRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer ${user.token}").build()
//        return chain.proceed(newRequest)
//    }
//}