package br.com.fernandodev.hinovaoficinas.core.database.dao


import androidx.room.*
import br.com.fernandodev.hinovaoficinas.model.VehicleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: VehicleEntity): Long

    @Update
    suspend fun update(entity: VehicleEntity)

    @Delete
    suspend fun delete(entity: VehicleEntity)

    @Query("SELECT * FROM vehicles WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): VehicleEntity?

    @Query("SELECT * FROM vehicles WHERE customerId = :customerId ORDER BY plate ASC")
    fun observeByCustomer(customerId: Long): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM vehicles ORDER BY plate ASC")
    fun observeAll(): Flow<List<VehicleEntity>>
}
