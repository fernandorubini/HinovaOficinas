package br.com.fernandodev.hinovaoficinas.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payments")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val serviceOrderId: Long,
    val method: String = "PIX",
    val amount: Double = 0.0,
    val status: String = "PENDING",
    val createdAtEpochMs: Long = System.currentTimeMillis()
)
