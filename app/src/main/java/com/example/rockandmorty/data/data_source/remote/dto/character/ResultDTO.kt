package com.example.rockandmorty.data.data_source.remote.dto.character

data class ResultDTO(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LocationDTO,
    val name: String,
    val origin: OriginDTO,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)