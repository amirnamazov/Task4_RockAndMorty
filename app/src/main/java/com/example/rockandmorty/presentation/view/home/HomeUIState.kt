package com.example.rockandmorty.presentation.view.home

import com.example.rockandmorty.domain.model.character.Character

sealed class HomeUIState(val message: String? = null) {
    data object Loading : HomeUIState()
    data class Success(val data: Character) : HomeUIState()
    class Error(message: String) : HomeUIState(message)
    data object ConnectionError : HomeUIState()
    data object Empty : HomeUIState()
}