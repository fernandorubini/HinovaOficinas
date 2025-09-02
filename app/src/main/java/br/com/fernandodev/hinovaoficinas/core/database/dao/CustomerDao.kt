package br.com.fernandodev.hinovaoficinas.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.fernandodev.hinovaoficinas.model.CustomerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CustomerEntity): Long

    @Update
    suspend fun update(entity: CustomerEntity)

    @Delete
    suspend fun delete(entity: CustomerEntity)

    @Query("SELECT * FROM customers WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): CustomerEntity?

    @Query("SELECT * FROM customers ORDER BY name ASC")
    fun observeAll(): Flow<List<CustomerEntity>>

    @Query("""
        SELECT * FROM customers
        WHERE name  LIKE '%' || :query || '%'
           OR phone LIKE '%' || :query || '%'
           OR doc   LIKE '%' || :query || '%'
        ORDER BY name ASC
    """)
    fun search(query: String): Flow<List<CustomerEntity>>
}
