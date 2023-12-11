package com.example.rockandmorty.data.repository

import com.example.rockandmorty.data.data_source.remote.api.CharacterApi
import com.example.rockandmorty.data.data_source.remote.dto.character.CharacterDTO
import com.example.rockandmorty.domain.repository.CharacterRepository
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val api: CharacterApi) :
    CharacterRepository {

    override suspend fun getCharacters(
        page: Int,
        gender: String,
        status: String
    ): Response<CharacterDTO> = api.getCharacters(page, gender, status)
}