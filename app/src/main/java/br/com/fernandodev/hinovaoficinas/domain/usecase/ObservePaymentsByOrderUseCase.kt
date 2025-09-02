package br.com.fernandodev.hinovaoficinas.domain.usecase

import br.com.fernandodev.hinovaoficinas.domain.repository.ServiceOrderRepository
import br.com.fernandodev.hinovaoficinas.domain.model.Payment
import kotlinx.coroutines.flow.Flow

class ObservePaymentsByOrderUseCase(
    private val repository: ServiceOrderRepository
) {
    operator fun invoke(osId: Long): Flow<List<Payment>> =
        repository.observePaymentsByOrder(orderId = osId)
}
