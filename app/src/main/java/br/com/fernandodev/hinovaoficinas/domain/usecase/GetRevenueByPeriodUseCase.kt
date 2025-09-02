package br.com.fernandodev.hinovaoficinas.domain.usecase

import br.com.fernandodev.hinovaoficinas.domain.repository.ServiceOrderRepository
import java.math.BigDecimal

class GetRevenueByPeriodUseCase(
    private val repository: ServiceOrderRepository
) {
    suspend operator fun invoke(from: Long, to: Long): BigDecimal =
        repository.sumPaymentsByPeriod(from, to)
}
