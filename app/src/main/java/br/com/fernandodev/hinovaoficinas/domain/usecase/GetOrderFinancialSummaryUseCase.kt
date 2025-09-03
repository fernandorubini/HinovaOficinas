package br.com.fernandodev.hinovaoficinas.domain.usecase

import br.com.fernandodev.hinovaoficinas.domain.model.OrderFinancialSummary
import br.com.fernandodev.hinovaoficinas.domain.repository.ServiceOrderRepository
import java.math.BigDecimal

class GetOrderFinancialSummaryUseCase(
    private val repository: ServiceOrderRepository
) {

    suspend operator fun invoke(osId: Long): OrderFinancialSummary {
        repository.getOrderById(osId) ?: error("OS não encontrada")

        val itemsTotal    = repository.sumItems(osId)
        val discount      = BigDecimal.ZERO
        val totalDue      = itemsTotal.subtract(discount).max(BigDecimal.ZERO)
        val paymentsTotal = repository.sumPayments(osId)
        val outstanding   = totalDue.subtract(paymentsTotal)

        return OrderFinancialSummary(
            itemsTotal    = itemsTotal,
            discount      = discount,
            paymentsTotal = paymentsTotal,
            totalDue      = totalDue,
            outstanding   = outstanding
        )
    }
}
