package com.example.rockandmorty.di.character

import com.example.rockandmorty.data.data_source.remote.api.CharacterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object CharacterApiModule {

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): CharacterApi = retrofit.create(CharacterApi :: class.java)
}