package com.example.rockandmorty.presentation.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.rockandmorty.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: CharacterRepository) : ViewModel() {

    fun getResults(gender: String, status: String) =
        repository.getCharacters(gender, status).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty()
        )
}