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

    val results get() = repository.getCharacters().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())
}