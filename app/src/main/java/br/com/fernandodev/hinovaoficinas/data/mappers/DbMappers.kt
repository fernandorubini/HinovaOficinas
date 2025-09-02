// app/src/main/java/br/com/fernandodev/hinovaoficinas/data/mappers/DbMappers.kt
package br.com.fernandodev.hinovaoficinas.data.mappers

import br.com.fernandodev.hinovaoficinas.core.database.entities.*
import br.com.fernandodev.hinovaoficinas.domain.model.*
import java.util.Locale

private fun String.toDomainStatus(): ServiceOrderStatus {
    return when (this.trim().uppercase(Locale.ROOT)) {
        // padrões do app
        "OPEN" -> ServiceOrderStatus.OPEN
        "IN_PROGRESS" -> ServiceOrderStatus.IN_PROGRESS
        "CLOSED" -> ServiceOrderStatus.CLOSED
        // possíveis aliases gravados no banco
        "ABERTA" -> ServiceOrderStatus.OPEN
        "EM_ANDAMENTO" -> ServiceOrderStatus.IN_PROGRESS
        "FECHADA" -> ServiceOrderStatus.CLOSED
        else -> ServiceOrderStatus.OPEN
    }
}

fun ServiceOrderEntity.toDomain(): ServiceOrder =
    ServiceOrder(
        id = id,
        customerName = customerName,
        vehiclePlate = vehiclePlate,
        description = description,
        status = status.toDomainStatus(),   // <-- aqui usamos a normalização
        totalAmount = totalAmount,
        createdAtEpochMs = createdAtEpochMs
    )

// (mantenha os demais mappers como já estão)
fun ServiceOrderItemEntity.toDomain(): ServiceOrderItem =
    ServiceOrderItem(
        id = id,
        serviceOrderId = serviceOrderId,
        itemName = itemName,
        quantity = quantity,
        unitPrice = unitPrice,
        totalPrice = quantity * unitPrice
    )

fun PaymentEntity.toDomain(): Payment =
    Payment(
        id = id,
        serviceOrderId = serviceOrderId,
        method = method,
        amount = amount,
        status = status,
        createdAtEpochMs = createdAtEpochMs
    )
