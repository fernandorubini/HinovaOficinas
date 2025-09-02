package br.com.fernandodev.hinovaoficinas.domain.model

data class ServiceOrderItem(
    val id: Long,
    val serviceOrderId: Long,
    val itemName: String,
    val quantity: Int,
    val unitPrice: Double,
    val totalPrice: Double
)
