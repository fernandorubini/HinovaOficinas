package br.com.fernandodev.hinovaoficinas.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_orders")
data class ServiceOrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val customerName: String,
    val vehiclePlate: String,
    val description: String? = null,
    val status: String = "OPEN",     // OPEN, IN_PROGRESS, CLOSED
    val totalAmount: Double = 0.0,
    val createdAtEpochMs: Long = System.currentTimeMillis()
)
