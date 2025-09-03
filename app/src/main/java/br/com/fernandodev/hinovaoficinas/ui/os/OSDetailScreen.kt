package br.com.fernandodev.hinovaoficinas.ui.os

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OSDetailScreen(
    osId: Long,
    onBack: () -> Unit,
    viewModel: OSViewModel = koinViewModel()
) {
    LaunchedEffect(osId) {
        viewModel.loadTotals(osId)
    }

    val totals by viewModel.totals.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "OS #$osId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { innerPadding: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Resumo",
                style = MaterialTheme.typography.titleMedium
            )
            Text("Itens: ${totals.items}")
            Text("Pagamentos: ${totals.payments}")

        }
    }
}
