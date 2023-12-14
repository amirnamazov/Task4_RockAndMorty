package com.example.rockandmorty.presentation.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.rockandmorty.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.properties.Delegates

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: CharacterRepository) : ViewModel() {

    lateinit var characterParams: List<CharacterParams>

    private fun getMap() = mapOf(
        "name" to searchText,
        "gender" to characterParams[0].run { params[selectedPos] },
        "status" to characterParams[1].run { params[selectedPos] },
    )

    fun getResults() = repository.getCharacters(getMap()).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty()
    )

    private val sharedFlowSearch = MutableSharedFlow<String>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    var searchText by Delegates.observable("") { _, oldValue, newValue ->
        if (oldValue != newValue) sharedFlowSearch.tryEmit(newValue)
    }

    private val _liveDataSearch = MutableLiveData<String>()
    val liveDataSearch: LiveData<String> get() = _liveDataSearch

    init {
        viewModelScope.launch(Dispatchers.IO) {
            sharedFlowSearch.debounce(300).collect {
                withContext(Dispatchers.Main) {
                    _liveDataSearch.value = it
                }
            }
        }
    }
}