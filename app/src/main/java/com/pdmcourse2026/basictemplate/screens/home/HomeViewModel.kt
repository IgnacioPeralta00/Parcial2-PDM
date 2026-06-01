package com.pdmcourse2026.basictemplate.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdmcourse2026.basictemplate.data.repositories.PlaceApiRepository
import com.pdmcourse2026.basictemplate.data.repositories.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val placesRepository : PlaceRepository = PlaceApiRepository()
    private val _uiState = MutableStateFlow(HomeUiState(loading = true))
    val uiState = _uiState.asStateFlow()


    init {
        loadHome()
    }

    fun loadHome() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, error = null)
            placesRepository.getPlaces()
                .onSuccess { places ->
                    _uiState.value = _uiState.value.copy(
                        places = places,
                        loading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = HomeUiState(
                        error = error.message,
                        loading = false
                    )
                }
        }
    }

    fun refreshHome() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            placesRepository.getPlaces()
                .onSuccess { places ->
                    _uiState.value = HomeUiState(
                        places = places,
                        loading = false,
                        isRefreshing = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = HomeUiState(
                        error = error.message,
                        loading = false,
                        isRefreshing = false
                    )
                }
        }
    }

    fun votePlace(placeId: Int) {
        viewModelScope.launch {
            placesRepository.votePlace(placeId)
                .onSuccess {
                    //loadHome() no hace nada jeje
                }
                .onFailure { error ->
                    _uiState.value = HomeUiState(
                        error = error.message,
                        loading = false
                    )
                }

        }
    }


}