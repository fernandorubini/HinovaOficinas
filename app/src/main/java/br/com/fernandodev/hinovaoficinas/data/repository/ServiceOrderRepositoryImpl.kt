package br.com.fernandodev.hinovaoficinas.data.repository

import br.com.fernandodev.hinovaoficinas.core.database.dao.PaymentDao
import br.com.fernandodev.hinovaoficinas.core.database.dao.ServiceOrderDao
import br.com.fernandodev.hinovaoficinas.core.database.dao.ServiceOrderItemDao
import br.com.fernandodev.hinovaoficinas.core.database.entities.PaymentEntity
import br.com.fernandodev.hinovaoficinas.core.database.entities.ServiceOrderEntity
import br.com.fernandodev.hinovaoficinas.core.database.entities.ServiceOrderItemEntity
import br.com.fernandodev.hinovaoficinas.core.database.PaymentMethod
import br.com.fernandodev.hinovaoficinas.data.mappers.toDomain
import br.com.fernandodev.hinovaoficinas.domain.model.*
import br.com.fernandodev.hinovaoficinas.domain.repository.ServiceOrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal

class ServiceOrderRepositoryImpl(
    private val orderDao: ServiceOrderDao,
    private val itemDao: ServiceOrderItemDao,
    private val paymentDao: PaymentDao
) : ServiceOrderRepository {

    /* ---------- Observes ---------- */

    override fun observeOrdersByStatus(status: ServiceOrderStatus): Flow<List<ServiceOrder>> =
        orderDao.observeByStatus(status.name).map { list -> list.map { it.toDomain() } }

    override fun observeItemsByOrder(orderId: Long): Flow<List<ServiceOrderItem>> =
        itemDao.observeByOrder(orderId).map { list -> list.map { it.toDomain() } }

    override fun observePaymentsByOrder(orderId: Long): Flow<List<Payment>> =
        paymentDao.observeByOrder(orderId).map { list -> list.map { it.toDomain() } }

    /* ---------- Comandos ---------- */

    override suspend fun addItem(
        osId: Long,
        kind: ItemKind,
        referenceId: Long?,
        description: String?,
        quantity: BigDecimal,
        unitPrice: BigDecimal
    ): Long {
        val name = when {
            !description.isNullOrBlank() -> description
            kind == ItemKind.PART && referenceId != null -> "Peça #$referenceId"
            kind == ItemKind.SERVICE && referenceId != null -> "Serviço #$referenceId"
            else -> "Item"
        }
        val entity = ServiceOrderItemEntity(
            serviceOrderId = osId,
            itemName = name,
            quantity = quantity.toInt(),
            unitPrice = unitPrice.toDouble()
        )
        return itemDao.insert(entity)
    }

    override suspend fun registerPayment(
        osId: Long,
        method: PaymentMethod,
        value: BigDecimal,
        paidAt: Long,
        externalId: String?
    ): Long {
        val entity = PaymentEntity(
            serviceOrderId = osId,
            method = method.name,            // persiste como String
            amount = value.toDouble(),
            status = "PAID",
            createdAtEpochMs = paidAt,
        )
        return paymentDao.insert(entity)
    }

    override suspend fun closeOrder(osId: Long, closedAt: Long) {
        orderDao.updateStatus(osId, ServiceOrderStatus.CLOSED.name)
        // Se quiser persistir o closedAt, adicione um DAO/coluna e atualize aqui.
    }

    override suspend fun updateStatus(osId: Long, newStatus: ServiceOrderStatus) {
        orderDao.updateStatus(osId, newStatus.name)
    }

    /* ---------- Consultas auxiliares ---------- */

    override suspend fun getOrderById(osId: Long): ServiceOrder? =
        orderDao.getById(osId)?.toDomain()

    override suspend fun sumItems(osId: Long): BigDecimal =
        BigDecimal.valueOf(itemDao.sumTotalByOrder(osId))

    override suspend fun sumPayments(osId: Long): BigDecimal =
        BigDecimal.valueOf(paymentDao.sumByOrder(osId))

    override suspend fun sumPaymentsByPeriod(from: Long, to: Long): BigDecimal =
        BigDecimal.valueOf(paymentDao.sumByPeriod(from, to))

    /* ---------- Abertura ---------- */

    override suspend fun openOrder(
        customerId: Long,
        vehicleId: Long,
        notes: String?,
        openedAt: Long
    ): Long {
        val entity = ServiceOrderEntity(
            id = 0, // autogerado
            customerName = "Cliente #$customerId",
            vehiclePlate = "VEÍC-$vehicleId",
            description = notes,
            status = ServiceOrderStatus.OPEN.name,
            totalAmount = 0.0,
            createdAtEpochMs = openedAt
        )
        return orderDao.insert(entity)
    }
}
