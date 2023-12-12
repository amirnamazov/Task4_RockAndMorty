package com.example.rockandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rockandmorty.data.data_source.remote.api.CharacterApi
import com.example.rockandmorty.data.data_source.remote.dto.character.ResultDTO
import com.example.rockandmorty.data.data_source.remote.paging_source.CharacterPagingResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharRepository @Inject constructor(private val api: CharacterApi) {

    fun getCharacters(): Flow<PagingData<ResultDTO>> = Pager(
        config = PagingConfig(
            pageSize = 12,
            maxSize = 90,
        ),
        pagingSourceFactory = { CharacterPagingResource(api) }
    ).flow
}