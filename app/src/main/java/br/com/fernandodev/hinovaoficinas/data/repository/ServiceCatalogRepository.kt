package br.com.fernandodev.hinovaoficinas.data.repository


import br.com.fernandodev.hinovaoficinas.core.database.dao.ServiceCatalogDao
import br.com.fernandodev.hinovaoficinas.model.ServiceCatalogEntity
import kotlinx.coroutines.flow.Flow

interface ServiceCatalogRepository {
    suspend fun upsert(entity: ServiceCatalogEntity): Long
    suspend fun delete(entity: ServiceCatalogEntity)
    fun observeAll(): Flow<List<ServiceCatalogEntity>>
    fun search(query: String): Flow<List<ServiceCatalogEntity>>
}

class ServiceCatalogRepositoryImpl(
    private val dao: ServiceCatalogDao
) : ServiceCatalogRepository {
    override suspend fun upsert(entity: ServiceCatalogEntity): Long = dao.insert(entity)
    override suspend fun delete(entity: ServiceCatalogEntity) = dao.delete(entity)
    override fun observeAll(): Flow<List<ServiceCatalogEntity>> = dao.observeAll()
    override fun search(query: String): Flow<List<ServiceCatalogEntity>> = dao.search(query)
}
