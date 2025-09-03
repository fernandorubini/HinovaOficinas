package br.com.fernandodev.hinovaoficinas.domain.usecase


import br.com.fernandodev.hinovaoficinas.core.database.PaymentMethod
import br.com.fernandodev.hinovaoficinas.domain.repository.ServiceOrderRepository
import java.math.BigDecimal

class CloseOrderWithPaymentUseCase(
    private val repository: ServiceOrderRepository,
    private val getSummary: GetOrderFinancialSummaryUseCase
) {
    data class Params(
        val osId: Long,
        val method: PaymentMethod,
        val value: BigDecimal,
        val paidAt: Long,
        val externalId: String?
    )


    suspend operator fun invoke(params: Params): java.math.BigDecimal {
        repository.registerPayment(
            osId = params.osId,
            method = params.method,
            value = params.value,
            paidAt = params.paidAt,
            externalId = params.externalId
        )

        val summary = getSummary(params.osId)
        if (summary.outstanding <= BigDecimal.ZERO) {
            repository.closeOrder(params.osId, closedAt = params.paidAt)
        }
        return summary.outstanding
    }
}
