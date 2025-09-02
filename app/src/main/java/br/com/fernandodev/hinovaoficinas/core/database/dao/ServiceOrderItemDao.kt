package br.com.fernandodev.hinovaoficinas.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fernandodev.hinovaoficinas.core.database.entities.ServiceOrderItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceOrderItemDao {

    @Query("SELECT * FROM service_order_items WHERE serviceOrderId = :orderId")
    fun observeByOrder(orderId: Long): Flow<List<ServiceOrderItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ServiceOrderItemEntity): Long

    // ▼ NOVO: soma de (quantidade * preço) por OS
    @Query("SELECT COALESCE(SUM(quantity * unitPrice), 0.0) FROM service_order_items WHERE serviceOrderId = :orderId")
    suspend fun sumTotalByOrder(orderId: Long): Double
}
