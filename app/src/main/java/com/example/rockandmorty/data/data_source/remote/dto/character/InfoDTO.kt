package com.example.rockandmorty.data.data_source.remote.dto.character

data class InfoDTO(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)