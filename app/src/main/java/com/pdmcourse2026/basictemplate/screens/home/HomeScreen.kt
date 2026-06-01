package com.pdmcourse2026.basictemplate.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdmcourse2026.basictemplate.screens.components.AppScaffold
import com.pdmcourse2026.basictemplate.screens.components.ErrorScreen
import com.pdmcourse2026.basictemplate.screens.components.OptionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  homeViewModel: HomeViewModel = viewModel(),
  onNavigateToVotes: () -> Unit
) {
  val uiState by homeViewModel.uiState.collectAsState()
  val context = LocalContext.current
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
          onRefresh = { homeViewModel.refreshHome() },
          modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
        ) {
          ErrorScreen(
            onRetryClick = { homeViewModel.refreshHome() },
            error = uiState.error
          )
        }
      }
    }
    else -> {
      AppScaffold(
        title = "Vota un lugar",
        actions = {
          IconButton(
            onClick = { onNavigateToVotes() },
          ) {
            Icon(
              imageVector = Icons.Default.HowToVote,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onBackground
            )
          }
        }
      ) { contentPadding ->
        PullToRefreshBox(
          isRefreshing = uiState.isRefreshing,
          onRefresh = { homeViewModel.refreshHome() },
          modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
        ) {
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(uiState.places) { place ->
              OptionCard(
                place = place,
                onPlaceClick = { placeId -> homeViewModel.votePlace(placeId)
                  Toast.makeText(context, "Voto emitido!", Toast.LENGTH_SHORT).show()},
                isVoteScreen = false
              )
            }
          }
        }
      }
    }
  }
}