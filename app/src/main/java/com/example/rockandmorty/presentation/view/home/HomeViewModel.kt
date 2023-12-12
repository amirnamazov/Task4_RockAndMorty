package com.example.rockandmorty.presentation.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.rockandmorty.data.repository.CharRepository
import com.example.rockandmorty.domain.use_cases.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: CharacterUseCase,
    private val repository: CharRepository
) : ViewModel() {

//    private val _resCharacters = MutableLiveData<HomeUIState>()
//    val resCharacters: LiveData<HomeUIState> get() = _resCharacters
//
//    fun getCharacters(page: Int, gender: String = "", status: String = "") =
//        useCase.getCharacters(page, gender, status).fetchData(_resCharacters)

    val results get() = repository.getCharacters()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), PagingData.empty())

//    private fun Flow<ResourceState<Character>>.fetchData(livedata: MutableLiveData<HomeUIState>) =
//        viewModelScope.launch(Dispatchers.IO) {
//            this@fetchData.collect { res ->
//                withContext(Dispatchers.Main) {
//                    livedata.value = when (res) {
//                        is ResourceState.ConnectionError -> HomeUIState.ConnectionError
//                        is ResourceState.Error -> HomeUIState.Error(res.message!!)
//                        is ResourceState.Loading -> HomeUIState.Loading
//                        is ResourceState.Success ->
//                            if (res.data?.results.isNullOrEmpty()) HomeUIState.Empty
//                            else HomeUIState.Success(res.data!!)
//                    }
//                }
//            }
//        }
}