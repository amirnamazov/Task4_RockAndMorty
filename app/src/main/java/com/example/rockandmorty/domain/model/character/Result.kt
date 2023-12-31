package com.example.rockandmorty.domain.model.character

import java.io.Serializable

data class Result(
    val id: Int?,
    val name: String?,
    val created: String?,
    val gender: String?,
    val image: String?,
    val locationName: String?,
    val originName: String?,
    val species: String?,
    val status: String?,
    val type: String?,
) : Serializable