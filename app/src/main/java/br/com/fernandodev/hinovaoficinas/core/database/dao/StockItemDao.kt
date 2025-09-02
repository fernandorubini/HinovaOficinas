package br.com.fernandodev.hinovaoficinas.core.database.dao


import androidx.room.*
import br.com.fernandodev.hinovaoficinas.model.StockItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StockItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: StockItemEntity): Long

    @Update
    suspend fun update(entity: StockItemEntity)

    @Delete
    suspend fun delete(entity: StockItemEntity)

    @Query("SELECT * FROM stock_items ORDER BY name ASC")
    fun observeAll(): Flow<List<StockItemEntity>>

    @Query("UPDATE stock_items SET quantity = quantity + :delta WHERE id = :id")
    suspend fun adjustQuantity(id: Long, delta: Int)

    @Query("SELECT * FROM stock_items WHERE id = :id LIMIT 1")
    suspend fun getByIdSync(id: Long): StockItemEntity?

    @Transaction
    suspend fun decrementStockOrThrow(id: Long, quantity: Int) {
        val item = getByIdSync(id) ?: throw IllegalArgumentException("Item de estoque não encontrado")
        val newQty = item.quantity - quantity
        if (newQty < 0) throw IllegalStateException("Estoque insuficiente para o item ${item.name}")
        adjustQuantity(id, -quantity)
    }
}
