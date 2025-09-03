package br.com.fernandodev.hinovaoficinas.core.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


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
    val osId: Long,
    val uri: String,
    val timestamp: Long
)
