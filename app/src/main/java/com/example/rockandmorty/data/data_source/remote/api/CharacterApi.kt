package com.example.rockandmorty.data.data_source.remote.api

import com.example.rockandmorty.data.data_source.remote.dto.character.CharacterDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharacterDTO>
}