package br.com.fernandodev.hinovaoficinas.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fernandodev.hinovaoficinas.core.database.entities.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {

    @Query("SELECT * FROM payments WHERE serviceOrderId = :orderId")
    fun observeByOrder(orderId: Long): Flow<List<PaymentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(payment: PaymentEntity): Long

    // ▼ NOVO: soma dos pagamentos por OS
    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM payments WHERE serviceOrderId = :orderId")
    suspend fun sumByOrder(orderId: Long): Double

    @Query("""
    SELECT COALESCE(SUM(amount), 0.0)
    FROM payments
    WHERE createdAtEpochMs BETWEEN :from AND :to
""")
    suspend fun sumByPeriod(from: Long, to: Long): Double

}
