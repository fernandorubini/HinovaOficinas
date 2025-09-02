package br.com.fernandodev.hinovaoficinas.data.repository


import br.com.fernandodev.hinovaoficinas.core.database.dao.CustomerDao
import br.com.fernandodev.hinovaoficinas.model.CustomerEntity
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun upsert(entity: CustomerEntity): Long
    suspend fun delete(entity: CustomerEntity)
    suspend fun getById(id: Long): CustomerEntity?
    fun observeAll(): Flow<List<CustomerEntity>>
    fun search(query: String): Flow<List<CustomerEntity>>
}

class CustomerRepositoryImpl(
    private val dao: CustomerDao
) : CustomerRepository {

    override suspend fun upsert(entity: CustomerEntity): Long =
        if (entity.id == 0L) {
            dao.insert(entity)                         // novo
        } else {
            dao.update(entity)                         // usa o @Update -> some o warning
            entity.id
        }

    override suspend fun delete(entity: CustomerEntity) = dao.delete(entity)
    override suspend fun getById(id: Long) = dao.getById(id)
    override fun observeAll() = dao.observeAll()
    override fun search(query: String) = dao.search(query)
}

