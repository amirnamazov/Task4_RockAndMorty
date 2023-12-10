package com.example.rockandmorty.di

import com.example.rockandmorty.common.Constants.base_url
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor = Interceptor { chain ->
        val url: HttpUrl = chain.request().url().newBuilder()
            .build()

        val request: Request = chain.request().newBuilder()
            .url(url)
            .build()

        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun providesFactory(): GsonConverterFactory = GsonConverterFactory.create(
        GsonBuilder().setLenient().create()
    )

    @Provides
    @Singleton
    fun providesInstance(client: OkHttpClient, factory: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(factory)
            .baseUrl(base_url)
            .build()
}