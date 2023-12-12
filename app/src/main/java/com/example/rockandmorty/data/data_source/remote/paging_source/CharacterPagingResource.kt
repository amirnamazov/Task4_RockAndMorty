package com.example.rockandmorty.data.data_source.remote.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rockandmorty.data.data_source.remote.api.CharacterApi
import com.example.rockandmorty.data.data_source.remote.dto.character.ResultDTO
import javax.inject.Inject

class CharacterPagingResource @Inject constructor(private val api: CharacterApi):
    PagingSource<Int, ResultDTO>() {

    override fun getRefreshKey(state: PagingState<Int, ResultDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultDTO> {
        val page = params.key ?: 1
        return try {
            val res = api.getCharacters(page, "", "")
            if (res.isSuccessful) {
                LoadResult.Page(
                    data = res.body()?.results.orEmpty(),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = page + 1
                )
            } else throw Exception()
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}