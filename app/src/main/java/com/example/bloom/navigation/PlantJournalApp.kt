package com.example.bloom.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bloom.data.repository.AuthRepository
import com.example.bloom.data.repository.PlantRepository
import com.example.bloom.service.AIService
import com.example.bloom.ui.auth.AuthScreen
import com.example.bloom.ui.capture.CaptureScreen
import com.example.bloom.ui.detail.DiscoveryDetailScreen
import com.example.bloom.ui.journal.JournalListScreen
import com.example.bloom.viewmodel.AuthViewModel
import com.example.bloom.viewmodel.CaptureViewModel
import com.example.bloom.viewmodel.DetailViewModel
import com.example.bloom.viewmodel.JournalViewModel

@Composable
fun PlantJournalApp(
    navController: NavController,
    authRepository: AuthRepository,
    plantRepository: PlantRepository
) {
    val isUserLoggedIn by authRepository.isUserLoggedIn.collectAsState()

    val startDestination = remember(isUserLoggedIn) {
        if (isUserLoggedIn) "journal" else "auth"
    }

    NavHost(
        navController = navController as NavHostController,
        startDestination = startDestination
    ) {
        composable("auth") {
            val authViewModel = remember {
                AuthViewModel(authRepository)
            }
            AuthScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }

        composable("journal") {
            val journalViewModel = remember {
                JournalViewModel(plantRepository, authRepository)
            }
            JournalListScreen(
                navController = navController,
                viewModel = journalViewModel
            )
        }

        composable("capture") {
            val captureViewModel = remember {
                CaptureViewModel(plantRepository, AIService(), authRepository)
            }
            CaptureScreen(
                navController = navController,
                viewModel = captureViewModel
            )
        }

        composable("detail/{discoveryId}") { backStackEntry ->
            val discoveryId = backStackEntry.arguments?.getString("discoveryId")?.toLongOrNull() ?: 0L
            val detailViewModel = remember {
                DetailViewModel(plantRepository)
            }
            DiscoveryDetailScreen(
                navController = navController,
                discoveryId = discoveryId,
                viewModel = detailViewModel
            )
        }
    }
}