package br.com.fernandodev.hinovaoficinas.model


import androidx.room.*

@Entity(
    tableName = "vehicles",
    foreignKeys = [
        ForeignKey(
            entity = CustomerEntity::class,
            parentColumns = ["id"],
            childColumns = ["customerId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("customerId"), Index("plate")]
)
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerId: Long,
    val plate: String,
    val brand: String?,
    val model: String?,
    val year: Int?,
    val currentKm: Int?,
    val notes: String?
)
