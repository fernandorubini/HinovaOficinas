package br.com.fernandodev.hinovaoficinas.core.database.dao


import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.com.fernandodev.hinovaoficinas.core.database.relations.ServiceOrderWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceOrderRelationsDao {
    @Transaction
    @Query("SELECT * FROM service_orders WHERE id = :osId LIMIT 1")
    fun observeOrderWithRelations(osId: Long): Flow<ServiceOrderWithRelations?>

    @Transaction
    @Query("SELECT * FROM service_orders ORDER BY openedAt DESC")
    fun observeAllOrdersWithRelations(): Flow<List<ServiceOrderWithRelations>>
}
