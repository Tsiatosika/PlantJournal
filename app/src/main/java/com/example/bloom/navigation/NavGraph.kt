package com.example.bloom.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bloom.ui.auth.AuthScreen
import com.example.bloom.ui.capture.CaptureScreen
import com.example.bloom.ui.detail.DiscoveryDetailScreen
import com.example.bloom.ui.journal.JournalListScreen
import com.example.bloom.viewmodel.AuthViewModel

fun NavGraphBuilder.authGraph(navController: NavController) {
    composable(route = "auth") {
        val viewModel: AuthViewModel = viewModel()
        AuthScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    composable("journal") {
        JournalListScreen(navController = navController)
    }
    composable("capture") {
        CaptureScreen(navController = navController)
    }
    composable("detail/{discoveryId}") { backStackEntry ->
        val discoveryId = backStackEntry.arguments?.getString("discoveryId")?.toLongOrNull() ?: 0L
        DiscoveryDetailScreen(
            navController = navController,
            discoveryId = discoveryId
        )
    }
}