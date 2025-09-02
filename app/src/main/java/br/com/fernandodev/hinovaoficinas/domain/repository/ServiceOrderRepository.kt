package br.com.fernandodev.hinovaoficinas.domain.repository

import br.com.fernandodev.hinovaoficinas.domain.model.*
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import br.com.fernandodev.hinovaoficinas.core.database.PaymentMethod

interface ServiceOrderRepository {

    fun observeOrdersByStatus(status: ServiceOrderStatus): Flow<List<ServiceOrder>>
    fun observeItemsByOrder(orderId: Long): Flow<List<ServiceOrderItem>>
    fun observePaymentsByOrder(orderId: Long): Flow<List<Payment>>

    suspend fun addItem(
        osId: Long,
        kind: ItemKind,
        referenceId: Long?,
        description: String?,
        quantity: BigDecimal,
        unitPrice: BigDecimal
    ): Long

    suspend fun registerPayment(
        osId: Long,
        method: PaymentMethod,
        value: BigDecimal,
        paidAt: Long,
        externalId: String?
    ): Long

    suspend fun closeOrder(osId: Long, closedAt: Long)
    suspend fun updateStatus(osId: Long, newStatus: ServiceOrderStatus)

    /* ------- Consultas ------- */
    suspend fun getOrderById(osId: Long): ServiceOrder?
    suspend fun sumItems(osId: Long): BigDecimal
    suspend fun sumPayments(osId: Long): BigDecimal
    suspend fun sumPaymentsByPeriod(from: Long, to: Long): BigDecimal

    /* ------- Abertura ------- */
    suspend fun openOrder(
        customerId: Long,
        vehicleId: Long,
        notes: String?,
        openedAt: Long
    ): Long
}
