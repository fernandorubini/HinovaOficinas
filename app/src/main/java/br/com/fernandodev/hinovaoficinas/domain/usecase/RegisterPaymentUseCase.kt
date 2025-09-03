package br.com.fernandodev.hinovaoficinas.domain.usecase

import br.com.fernandodev.hinovaoficinas.core.database.PaymentMethod
import br.com.fernandodev.hinovaoficinas.domain.repository.ServiceOrderRepository
import java.math.BigDecimal

class RegisterPaymentUseCase(
    private val repository: ServiceOrderRepository
) {
    data class Params(
        val osId: Long,
        val method: PaymentMethod,
        val value: BigDecimal,
        val paidAt: Long,
        val externalId: String?
    )

    suspend operator fun invoke(params: Params): Long =
        repository.registerPayment(
            osId       = params.osId,
            method     = params.method,
            value      = params.value,
            paidAt     = params.paidAt,
            externalId = params.externalId
        )
}
