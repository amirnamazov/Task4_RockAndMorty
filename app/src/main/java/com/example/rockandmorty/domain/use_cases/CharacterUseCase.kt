package com.example.rockandmorty.domain.use_cases

import com.example.rockandmorty.common.IOService.getRemoteData
import com.example.rockandmorty.common.ResourceState
import com.example.rockandmorty.data.data_source.remote.mapper.CharacterMapper
import com.example.rockandmorty.domain.model.character.Character
import com.example.rockandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterUseCase @Inject constructor(private val repository: CharacterRepository) {

    fun getCharacters(page: Int): Flow<ResourceState<Character>> =
        getRemoteData(
            request = { repository.getCharacters(page) },
            mapper = { CharacterMapper.map(it) })
}