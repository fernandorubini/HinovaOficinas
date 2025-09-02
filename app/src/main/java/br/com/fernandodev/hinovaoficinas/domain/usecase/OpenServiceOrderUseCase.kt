package br.com.fernandodev.hinovaoficinas.domain.usecase


import br.com.fernandodev.hinovaoficinas.domain.repository.ServiceOrderRepository

class OpenServiceOrderUseCase(
    private val repository: ServiceOrderRepository
) {
    data class Params(
        val customerId: Long,
        val vehicleId: Long,
        val notes: String?,
        val openedAt: Long
    )

    suspend operator fun invoke(params: Params): Long =
        repository.openOrder(
            customerId = params.customerId,
            vehicleId = params.vehicleId,
            notes = params.notes,
            openedAt = params.openedAt
        )
}

