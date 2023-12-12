package com.example.rockandmorty.data.data_source.remote.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rockandmorty.data.data_source.remote.api.CharacterApi
import com.example.rockandmorty.data.data_source.remote.dto.character.CharacterDTO
import com.example.rockandmorty.data.data_source.remote.mapper.CharacterMapper
import com.example.rockandmorty.domain.model.character.Result
import javax.inject.Inject

class CharacterPagingResource @Inject constructor(
    private val api: CharacterApi,
    private val gender: String,
    private val status: String
) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: 1
        return try {
            val response = api.getCharacters(page, gender, status)
            val character: CharacterDTO? = response.body()
            if (!response.isSuccessful || character == null) throw Exception()
            val results: List<Result>? = CharacterMapper.map(character).results
            LoadResult.Page(
                data = results.orEmpty(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}