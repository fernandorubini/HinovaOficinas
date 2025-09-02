package br.com.fernandodev.hinovaoficinas.domain.model

data class Payment(
    val id: Long,
    val serviceOrderId: Long,
    val method: String,
    val amount: Double,
    val status: String,
    val createdAtEpochMs: Long
)
