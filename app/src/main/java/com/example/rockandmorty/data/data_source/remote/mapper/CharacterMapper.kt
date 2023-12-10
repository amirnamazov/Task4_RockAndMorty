package com.example.rockandmorty.data.data_source.remote.mapper

import com.example.rockandmorty.data.data_source.remote.dto.character.CharacterDTO
import com.example.rockandmorty.domain.model.character.Character
import com.example.rockandmorty.domain.model.character.Result

object CharacterMapper {
    fun map(characterDTO: CharacterDTO): Character = Character(
        pages = characterDTO.info.pages,
        results = characterDTO.results.map {
            Result(
                name = it.name,
                created = it.created,
                gender = it.gender,
                image = it.image,
                locationName = it.location.name,
                originName = it.origin.name,
                species = it.species,
                status = it.status,
                type = it.type,
            )
        }
    )
}