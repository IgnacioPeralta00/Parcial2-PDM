package com.pdmcourse2026.basictemplate

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdmcourse2026.basictemplate.screens.home.HomeScreen
import com.pdmcourse2026.basictemplate.screens.votes.VotesScreen

@Composable
fun Navigator() {
  val backStack = rememberNavBackStack(Routes.Home)

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = entryProvider {
      entry<Routes.Home> {
        HomeScreen(
          onNavigateToVotes = { backStack.add(Routes.Votes) }
        )
      }
      entry<Routes.Votes> {
        VotesScreen(
          onBack = { backStack.removeLastOrNull() }
        )
      }
    },
  )


}