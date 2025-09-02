package br.com.fernandodev.hinovaoficinas.ui.os

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import br.com.fernandodev.hinovaoficinas.domain.model.ServiceOrder
import br.com.fernandodev.hinovaoficinas.domain.model.ServiceOrderStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OSListScreen(
    onOpenDetail: (Long) -> Unit,
    viewModel: OSViewModel = koinViewModel()
) {
    var tabIndex by rememberSaveable { mutableStateOf(0) }

    // Abas: status do DOMÍNIO + rótulo para UI
    val tabs: List<Pair<ServiceOrderStatus, String>> = listOf(
        ServiceOrderStatus.OPEN to "Abertas",
        ServiceOrderStatus.IN_PROGRESS to "Em Execução",
        ServiceOrderStatus.DELIVERED to "Concluídas"
    )

    val status = tabs[tabIndex].first
    val orders: List<ServiceOrder> by viewModel
        .observeByStatus(status)
        .collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("Ordens de Serviço") }) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, (_, label) ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        text = { Text(label) }
                    )
                }
            }

            if (orders.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhuma OS")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = orders, key = { it.id }) { os ->
                        OSListItem(
                            os = os,
                            onClick = { onOpenDetail(os.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OSListItem(
    os: ServiceOrder,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "OS #${os.id}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text("Status: ${os.status}")
        }
    }
}
