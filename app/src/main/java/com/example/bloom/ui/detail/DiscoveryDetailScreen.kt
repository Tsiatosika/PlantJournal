package com.example.bloom.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bloom.data.model.PlantDiscovery
import com.example.bloom.utils.FileUtils
import com.example.bloom.viewmodel.DetailViewModel
import java.io.File

@Composable
fun DiscoveryDetailScreen(
    navController: NavController,
    discoveryId: Long,
    viewModel: DetailViewModel = viewModel()
) {
    val discovery by viewModel.getDiscovery(discoveryId).collectAsState(initial = null)
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DetailTopAppBar(
                onBack = { navController.popBackStack() },
                onDelete = { showDeleteDialog = true }
            )
        }
    ) { paddingValues ->
        if (discovery == null) {
            Text(
                text = "Chargement...",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        } else {
            DiscoveryDetailContent(
                discovery = discovery!!,
                paddingValues = paddingValues,
                onDelete = {
                    viewModel.deleteDiscovery(discovery!!)
                    navController.popBackStack()
                }
            )
        }
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                viewModel.deleteDiscovery(discovery!!)
                navController.popBackStack()
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    TopAppBar(
        title = { Text("Détail de la Découverte") },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
            }
        },
        actions = {
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Supprimer")
            }
        }
    )
}

@Composable
fun DiscoveryDetailContent(
    discovery: PlantDiscovery,
    paddingValues: PaddingValues,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        // Image
        AsyncImage(
            model = File(discovery.imagePath),
            contentDescription = discovery.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        // Content
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Détail Plante",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = discovery.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = discovery.summary,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Metadata
            Text(
                text = "Découverte le ${FileUtils.formatDetailedTimestamp(discovery.timestamp)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Supprimer la découverte") },
        text = { Text("Êtes-vous sûr de vouloir supprimer cette découverte ? Cette action est irréversible.") },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text("Supprimer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}