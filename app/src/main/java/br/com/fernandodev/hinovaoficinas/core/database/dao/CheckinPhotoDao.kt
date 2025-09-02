package br.com.fernandodev.hinovaoficinas.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fernandodev.hinovaoficinas.core.database.entities.CheckinPhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckinPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CheckinPhotoEntity): Long

    @Delete
    suspend fun delete(entity: CheckinPhotoEntity)

    @Query("SELECT * FROM checkin_photos WHERE osId = :osId ORDER BY timestamp DESC")
    fun observeByOrder(osId: Long): Flow<List<CheckinPhotoEntity>>
}
