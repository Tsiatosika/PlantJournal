package com.example.bloom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bloom.data.repository.MockAuthRepository
import com.example.bloom.data.repository.PlantRepository
import com.example.bloom.ui.auth.AuthScreen
import com.example.bloom.ui.capture.CaptureScreen
import com.example.bloom.ui.detail.DiscoveryDetailScreen
import com.example.bloom.ui.journal.JournalListScreen
import com.example.bloom.ui.theme.PlantJournalTheme
import com.example.bloom.viewmodel.AuthViewModel
import com.example.bloom.viewmodel.CaptureViewModel
import com.example.bloom.viewmodel.DetailViewModel
import com.example.bloom.viewmodel.JournalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantJournalApp()
        }
    }
}

@Composable
fun PlantJournalApp() {
    val navController = rememberNavController()
    val applicationContext = androidx.compose.ui.platform.LocalContext.current.applicationContext
    val plantJournalApp = applicationContext as PlantJournalApplication

    val authRepository = remember {
        MockAuthRepository()
    }

    val plantRepository = remember {
        PlantRepository(plantJournalApp.database.plantDao())
    }

    PlantJournalTheme {
        NavigationApp(
            navController = navController,
            authRepository = authRepository,
            plantRepository = plantRepository
        )
    }
}

@Composable
fun NavigationApp(
    navController: androidx.navigation.NavController,
    authRepository: com.example.bloom.data.repository.AuthRepository,
    plantRepository: com.example.bloom.data.repository.PlantRepository
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
                CaptureViewModel(
                    plantRepository,
                    com.example.bloom.service.AIService(),
                    authRepository
                )
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