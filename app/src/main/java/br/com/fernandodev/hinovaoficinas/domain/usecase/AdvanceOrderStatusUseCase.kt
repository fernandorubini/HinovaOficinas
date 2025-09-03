package br.com.fernandodev.hinovaoficinas.domain.usecase

import br.com.fernandodev.hinovaoficinas.domain.model.ServiceOrderStatus
import br.com.fernandodev.hinovaoficinas.domain.repository.ServiceOrderRepository

class AdvanceOrderStatusUseCase(
    private val repository: ServiceOrderRepository
) {
    data class Params(
        val osId: Long,
        val newStatus: ServiceOrderStatus
    )

    suspend operator fun invoke(params: Params) {
        repository.updateStatus(params.osId, params.newStatus)
    }
}
