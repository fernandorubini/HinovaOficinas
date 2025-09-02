package br.com.fernandodev.hinovaoficinas.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_order_items")
data class ServiceOrderItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val serviceOrderId: Long,
    val itemName: String,
    val quantity: Int = 1,
    val unitPrice: Double = 0.0
) {
    val totalPrice: Double get() = quantity * unitPrice
}
