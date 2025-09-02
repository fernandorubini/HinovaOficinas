package br.com.fernandodev.hinovaoficinas.data.repository


import br.com.fernandodev.hinovaoficinas.core.database.dao.StockItemDao
import br.com.fernandodev.hinovaoficinas.model.StockItemEntity
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun upsert(entity: StockItemEntity): Long
    suspend fun delete(entity: StockItemEntity)
    fun observeAll(): Flow<List<StockItemEntity>>
    suspend fun getByIdSync(id: Long): StockItemEntity?
    suspend fun adjustQuantity(id: Long, delta: Int)
    suspend fun decrementOrThrow(id: Long, quantity: Int)
}

class StockRepositoryImpl(
    private val dao: StockItemDao
) : StockRepository {
    override suspend fun upsert(entity: StockItemEntity): Long = dao.insert(entity)
    override suspend fun delete(entity: StockItemEntity) = dao.delete(entity)
    override fun observeAll(): Flow<List<StockItemEntity>> = dao.observeAll()
    override suspend fun getByIdSync(id: Long): StockItemEntity? = dao.getByIdSync(id)
    override suspend fun adjustQuantity(id: Long, delta: Int) = dao.adjustQuantity(id, delta)
    override suspend fun decrementOrThrow(id: Long, quantity: Int) = dao.decrementStockOrThrow(id, quantity)
}
