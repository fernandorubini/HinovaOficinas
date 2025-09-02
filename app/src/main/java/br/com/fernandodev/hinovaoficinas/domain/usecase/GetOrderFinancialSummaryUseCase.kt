package br.com.fernandodev.hinovaoficinas.domain.usecase

import br.com.fernandodev.hinovaoficinas.domain.model.OrderFinancialSummary
import br.com.fernandodev.hinovaoficinas.domain.repository.ServiceOrderRepository
import java.math.BigDecimal

class GetOrderFinancialSummaryUseCase(
    private val repository: ServiceOrderRepository
) {

    suspend operator fun invoke(osId: Long): OrderFinancialSummary {
        // valida existência da OS (mensagem atual em PT-BR como você colocou)
        repository.getOrderById(osId) ?: error("OS não encontrada")

        val itemsTotal    = repository.sumItems(osId)          // BigDecimal
        val discount      = BigDecimal.ZERO                    // ainda não há desconto no modelo
        val totalDue      = itemsTotal.subtract(discount).max(BigDecimal.ZERO)
        val paymentsTotal = repository.sumPayments(osId)       // BigDecimal
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
