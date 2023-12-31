package com.example.rockandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rockandmorty.data.data_source.remote.api.CharacterApi
import com.example.rockandmorty.data.data_source.remote.paging_source.CharacterPagingResource
import com.example.rockandmorty.domain.model.character.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val api: CharacterApi) {

    fun getCharacters(map: Map<String, String>): Flow<PagingData<Result>> = Pager(
        config = PagingConfig(
            pageSize = 12,
            maxSize = 90,
        ),
        pagingSourceFactory = { CharacterPagingResource(api, map) }
    ).flow
}