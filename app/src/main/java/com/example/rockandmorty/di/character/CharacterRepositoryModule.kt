package com.example.rockandmorty.di.character

import com.example.rockandmorty.data.repository.CharacterRepositoryImpl
import com.example.rockandmorty.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent :: class)
interface CharacterRepositoryModule {

    @Binds
    fun bindsNewsRepository(repository: CharacterRepositoryImpl): CharacterRepository
}