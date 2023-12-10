package com.example.rockandmorty.domain.repository

import com.example.rockandmorty.data.data_source.remote.dto.character.CharacterDTO
import retrofit2.Response

interface CharacterRepository {
    suspend fun getCharacters(page: Int): Response<CharacterDTO>
}