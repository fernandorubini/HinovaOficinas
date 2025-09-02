package br.com.fernandodev.hinovaoficinas.domain.usecase

import br.com.fernandodev.hinovaoficinas.domain.model.ItemKind
import br.com.fernandodev.hinovaoficinas.domain.repository.ServiceOrderRepository
import java.math.BigDecimal

class AddItemToOrderUseCase(
    private val repository: ServiceOrderRepository
) {
    data class Params(
        val osId: Long,
        val kind: ItemKind,
        val referenceId: Long?,     // obrigatório quando kind == PART
        val description: String?,
        val quantity: BigDecimal,
        val unitPrice: BigDecimal
    )

    suspend operator fun invoke(params: Params): Long =
        repository.addItem(
            osId = params.osId,
            kind = params.kind,
            referenceId = params.referenceId,
            description = params.description,
            quantity = params.quantity,
            unitPrice = params.unitPrice
        )
}