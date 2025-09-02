package br.com.fernandodev.hinovaoficinas.data.repository


import br.com.fernandodev.hinovaoficinas.core.database.dao.VehicleDao
import br.com.fernandodev.hinovaoficinas.model.VehicleEntity
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    suspend fun upsert(entity: VehicleEntity): Long
    suspend fun delete(entity: VehicleEntity)
    suspend fun getById(id: Long): VehicleEntity?
    fun observeAll(): Flow<List<VehicleEntity>>
    fun observeByCustomer(customerId: Long): Flow<List<VehicleEntity>>
}

class VehicleRepositoryImpl(
    private val dao: VehicleDao
) : VehicleRepository {
    override suspend fun upsert(entity: VehicleEntity): Long = dao.insert(entity)
    override suspend fun delete(entity: VehicleEntity) = dao.delete(entity)
    override suspend fun getById(id: Long): VehicleEntity? = dao.getById(id)
    override fun observeAll(): Flow<List<VehicleEntity>> = dao.observeAll()
    override fun observeByCustomer(customerId: Long): Flow<List<VehicleEntity>> =
        dao.observeByCustomer(customerId)
}
