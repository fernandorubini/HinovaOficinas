package br.com.fernandodev.hinovaoficinas.ui.os

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fernandodev.hinovaoficinas.domain.model.ServiceOrder
import br.com.fernandodev.hinovaoficinas.domain.model.ServiceOrderStatus
import br.com.fernandodev.hinovaoficinas.domain.repository.ServiceOrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class OSViewModel(
    private val repo: ServiceOrderRepository
) : ViewModel() {

    data class TotalsUi(
        val items: Int = 0,
        val payments: Int = 0
    )

    private val _totals = MutableStateFlow(TotalsUi())
    val totals: StateFlow<TotalsUi> = _totals.asStateFlow()

    /** Fluxo de OS por status (lista de domínio) */
    fun observeByStatus(status: ServiceOrderStatus): Flow<List<ServiceOrder>> =
        repo.observeOrdersByStatus(status)

    /** Carrega totais (para a tela de detalhes da OS) */
    fun loadTotals(orderId: Long) {
        viewModelScope.launch {
            val itemsCount = repo.observeItemsByOrder(orderId).firstOrNull()?.size ?: 0
            val paymentsCount = repo.observePaymentsByOrder(orderId).firstOrNull()?.size ?: 0
            _totals.value = TotalsUi(items = itemsCount, payments = paymentsCount)
        }
    }
}
