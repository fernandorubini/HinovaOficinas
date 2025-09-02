package br.com.fernandodev.hinovaoficinas.core.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Fotos do check-in associadas a uma OS.
 * Campos alinham com o DAO (osId, timestamp).
 */
@Entity(
    tableName = "checkin_photos",
    foreignKeys = [
        ForeignKey(
            entity = ServiceOrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["osId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["osId"])]
)
data class CheckinPhotoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val osId: Long,        // id da OS (bate com a query do DAO)
    val uri: String,       // caminho/URI da foto
    val timestamp: Long    // epoch millis (usado no ORDER BY da query)
)
