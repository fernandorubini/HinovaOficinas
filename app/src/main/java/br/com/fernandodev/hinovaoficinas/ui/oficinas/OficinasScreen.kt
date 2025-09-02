package br.com.fernandodev.hinovaoficinas.ui.oficinas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.fernandodev.hinovaoficinas.domain.model.Oficina
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OficinasScreen(
    viewModel: OficinasViewModel = koinViewModel(),
    onOficinaClick: (Oficina) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.load() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Oficinas") }
            )
        }
    ) { inner ->
        when {
            state.isLoading -> {
                Box(
                    Modifier
                        .padding(inner)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }

            state.error != null -> {
                Box(
                    Modifier
                        .padding(inner)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Erro: ${state.error}")
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = viewModel::retry) { Text("Tentar novamente") }
                    }
                }
            }

            state.items.isEmpty() -> {
                Box(
                    Modifier
                        .padding(inner)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text("Nenhuma oficina encontrada") }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(inner)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.items, key = { it.id }) { of ->
                        OficinaCard(oficina = of) { onOficinaClick(of) }
                    }
                }
            }
        }
    }
}

@Composable
private fun OficinaCard(oficina: Oficina, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                oficina.nome,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            if (oficina.descricaoCurta.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(oficina.descricaoCurta, style = MaterialTheme.typography.bodyMedium)
            }
            if (oficina.endereco.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(oficina.endereco, style = MaterialTheme.typography.bodySmall)
            }
            if (oficina.telefone.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text("Tel: ${oficina.telefone}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
