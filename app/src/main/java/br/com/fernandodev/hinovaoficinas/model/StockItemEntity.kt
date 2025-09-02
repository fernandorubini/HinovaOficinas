package br.com.fernandodev.hinovaoficinas.model


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "stock_items",
    indices = [Index("name"), Index("sku")]
)
data class StockItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val sku: String?,
    val costPrice: BigDecimal?,
    val salePrice: BigDecimal?,
    val quantity: Int = 0,
    val minQuantity: Int = 0
)
