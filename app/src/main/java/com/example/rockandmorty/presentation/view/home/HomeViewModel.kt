package com.example.rockandmorty.presentation.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rockandmorty.common.ResourceState
import com.example.rockandmorty.domain.model.character.Character
import com.example.rockandmorty.domain.use_cases.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: CharacterUseCase) : ViewModel() {

    private val _resCharacters = MutableLiveData<HomeUIState>()
    val resCharacters: LiveData<HomeUIState> get() = _resCharacters

    fun getCharacters(page: Int, gender: String = "", status: String = "") =
        useCase.getCharacters(page, gender, status).fetchData(_resCharacters)

    private fun Flow<ResourceState<Character>>.fetchData(livedata: MutableLiveData<HomeUIState>) =
        viewModelScope.launch(Dispatchers.IO) {
            this@fetchData.collect { res ->
                withContext(Dispatchers.Main) {
                    livedata.value = when (res) {
                        is ResourceState.ConnectionError -> HomeUIState.ConnectionError
                        is ResourceState.Error -> HomeUIState.Error(res.message!!)
                        is ResourceState.Loading -> HomeUIState.Loading
                        is ResourceState.Success ->
                            if (res.data?.results.isNullOrEmpty()) HomeUIState.Empty
                            else HomeUIState.Success(res.data!!)
                    }
                }
            }
        }
}