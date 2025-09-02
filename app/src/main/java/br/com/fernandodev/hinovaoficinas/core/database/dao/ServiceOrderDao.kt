package br.com.fernandodev.hinovaoficinas.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fernandodev.hinovaoficinas.core.database.entities.ServiceOrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceOrderDao {

    @Query("SELECT * FROM service_orders WHERE status = :status ORDER BY createdAtEpochMs DESC")
    fun observeByStatus(status: String): Flow<List<ServiceOrderEntity>>

    @Query("SELECT * FROM service_orders WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ServiceOrderEntity?

    @Query("UPDATE service_orders SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: ServiceOrderEntity): Long
}
