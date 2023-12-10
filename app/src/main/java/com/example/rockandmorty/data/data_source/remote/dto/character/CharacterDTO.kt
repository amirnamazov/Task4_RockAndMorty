package com.example.rockandmorty.data.data_source.remote.dto.character

data class CharacterDTO(
    val info: InfoDTO,
    val results: List<ResultDTO>
)