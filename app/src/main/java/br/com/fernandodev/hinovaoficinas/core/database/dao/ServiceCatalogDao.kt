package br.com.fernandodev.hinovaoficinas.core.database.dao


import androidx.room.*
import br.com.fernandodev.hinovaoficinas.model.ServiceCatalogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceCatalogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ServiceCatalogEntity): Long

    @Update
    suspend fun update(entity: ServiceCatalogEntity)

    @Delete
    suspend fun delete(entity: ServiceCatalogEntity)

    @Query("SELECT * FROM service_catalog ORDER BY name ASC")
    fun observeAll(): Flow<List<ServiceCatalogEntity>>

    @Query("SELECT * FROM service_catalog WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun search(query: String): Flow<List<ServiceCatalogEntity>>
}
