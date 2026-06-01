package com.pdmcourse2026.basictemplate.screens.votes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun VotesScreen(
    votesViewModel: VotesViewModel = viewModel()
) {
    val uiState by votesViewModel.uiState.collectAsState()
}