package br.com.fernandodev.hinovaoficinas.domain.model

data class ServiceOrder(
    val id: Long,
    val customerName: String,
    val vehiclePlate: String,
    val description: String?,
    val status: ServiceOrderStatus,
    val totalAmount: Double,
    val createdAtEpochMs: Long
)
