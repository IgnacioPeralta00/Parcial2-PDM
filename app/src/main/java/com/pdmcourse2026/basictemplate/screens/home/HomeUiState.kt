package com.pdmcourse2026.basictemplate.screens.home

import com.pdmcourse2026.basictemplate.models.Place

data class HomeUiState(
    val places : List<Place> = emptyList(),
    val loading : Boolean = false,
    val error : String? = null,
    val isRefreshing : Boolean = false
)
