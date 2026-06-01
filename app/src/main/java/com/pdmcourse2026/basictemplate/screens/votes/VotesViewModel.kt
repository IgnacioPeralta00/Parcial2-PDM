package com.pdmcourse2026.basictemplate.screens.votes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdmcourse2026.basictemplate.data.repositories.PlaceApiRepository
import com.pdmcourse2026.basictemplate.data.repositories.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VotesViewModel : ViewModel() {
    private val placesRepository : PlaceRepository = PlaceApiRepository()
    private val _uiState = MutableStateFlow(VotesUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    /*init {
        loadVotes()
    }*/

    fun loadVotes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, error = null)
            placesRepository.getPlaces()
                .onSuccess { places ->
                    // Ordenar de mas votos a menos y pasar la UI
                    _uiState.value = VotesUiState(
                        places = places.sortedByDescending { it.votes },
                        loading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = VotesUiState(
                        error = error.message,
                        loading = false
                    )
                }
        }
    }

    fun refreshVotes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            placesRepository.getPlaces()
                .onSuccess { places ->
                    _uiState.value = VotesUiState(
                        places = places.sortedByDescending { it.votes },
                        loading = false,
                        isRefreshing = false
                    )
                }
                    .onFailure { error ->
                    _uiState.value = VotesUiState(
                        error = error.message,
                        loading = false,
                        isRefreshing = false
                    )
                    }
        }
    }
}