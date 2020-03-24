package com.razbyte.architectureexample.business.providers

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val providersModule: Module = module {

    // region Internet providers

    // OkHttpClient
    factory { provideOkHttpClient() }

    // Retrofit for GitApi
    single(named(GitApi.NAME)) {
        provideRetrofit(get(), GitApi.BASE_URL)
    }

    // Retrofit for GitRawApi
    single(named(GitRawApi.NAME)) {
        provideRetrofit(get(), GitRawApi.BASE_URL)
    }

    // Client for GitApi
    factory { provideGitApi(get(named(GitApi.NAME))) }

    // Client for GitRawApi
    factory { provideGitRawApi(get(named(GitRawApi.NAME))) }

    // endregion

    // region Local providers

    // endregion

}

fun provideOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient().newBuilder().addInterceptor(interceptor).build()
}

fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

// region Apis

fun provideGitApi(retrofit: Retrofit): GitApi {
    return retrofit.create(GitApi::class.java)
}

fun provideGitRawApi(retrofit: Retrofit): GitRawApi {
    return retrofit.create(GitRawApi::class.java)
}

// endregion
