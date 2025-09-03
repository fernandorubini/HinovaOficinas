// File: app/src/main/java/br/com/fernandodev/hinovaoficinas/ui/details/OficinasScreen.kt
package br.com.fernandodev.hinovaoficinas.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fernandodev.hinovaoficinas.core.network.HinovaWebRepository
import br.com.fernandodev.hinovaoficinas.domain.model.Oficina

@Composable
fun OficinasScreen(
    web: HinovaWebRepository
) {
    var result by remember { mutableStateOf<Result<List<Oficina>>?>(null) }

    LaunchedEffect(Unit) {
        result = web.getOficinas(codigoAssociacao = 601, cpfAssociado = "")
    }

    when (val r = result) {
        null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        else -> r
            .onSuccess { list ->
                if (list.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Nenhuma oficina encontrada")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(list) { of ->
                            Text(of.nome, style = MaterialTheme.typography.titleMedium)
                            if (of.endereco.isNotBlank()) {
                                Text(of.endereco, style = MaterialTheme.typography.bodyMedium)
                            }
                            Divider()
                        }
                    }
                }
            }
            .onFailure { e ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Erro: ${e.message ?: "desconhecido"}")
                }
            }
    }
}
