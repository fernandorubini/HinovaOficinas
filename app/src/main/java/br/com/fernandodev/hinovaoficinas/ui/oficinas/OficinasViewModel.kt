package br.com.fernandodev.hinovaoficinas.ui.oficinas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fernandodev.hinovaoficinas.core.network.HinovaWebRepository
import br.com.fernandodev.hinovaoficinas.domain.model.Oficina
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OficinasViewModel(
    private val web: HinovaWebRepository
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val items: List<Oficina> = emptyList(),
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState(isLoading = true))
    val state: StateFlow<UiState> = _state.asStateFlow()

    private var loadedOnce = false

    fun load(codigoAssociacao: Int = 601, cpfAssociado: String? = null, force: Boolean = false) {
        if (loadedOnce && !force) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = web.getOficinas(codigoAssociacao, cpfAssociado.orEmpty())
            _state.value = result.fold(
                onSuccess = { list ->
                    loadedOnce = true
                    UiState(items = list)
                },
                onFailure = { e ->
                    UiState(error = e.message ?: "Falha ao carregar oficinas.")
                }
            )
        }
    }

    fun retry() = load(force = true)
}
