package com.pdmcourse2026.basictemplate.screens.votes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdmcourse2026.basictemplate.screens.components.AppScaffold
import com.pdmcourse2026.basictemplate.screens.components.ErrorScreen
import com.pdmcourse2026.basictemplate.screens.components.OptionCard

@Composable
fun VotesScreen(
    votesViewModel: VotesViewModel = viewModel(),
    onBack: () -> Unit
) {
    val uiState by votesViewModel.uiState.collectAsState()
    when {
        uiState.loading -> {
            AppScaffold(title = "") { contentPadding ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        uiState.error != null -> {
            AppScaffold(
                title = ""
            ) { contentPadding ->
                PullToRefreshBox(
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = { /*viewModel.refreshHome()*/ },
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize()
                ) {
                    ErrorScreen(
                        onRetryClick = { /*viewModel.refresh()*/ },
                        error = uiState.error
                    )
                }
            }
        }
        else -> {
            AppScaffold(
                title = "Vota un lugar",
                navigationIcon = {
                    IconButton(
                        onClick = { onBack() },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            ) { contentPadding ->
                PullToRefreshBox(
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = { /*viewModel.refresh()*/ },
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.places) { place ->
                            OptionCard(
                                place = place,
                                onPlaceClick = { /**/ },
                                isVoteScreen = true
                            )
                        }
                    }
                }
            }
        }
    }
}