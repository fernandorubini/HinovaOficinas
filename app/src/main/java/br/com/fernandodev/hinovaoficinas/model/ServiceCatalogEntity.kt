package br.com.fernandodev.hinovaoficinas.model


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.fernandodev.hinovaoficinas.core.database.ItemKind
import java.math.BigDecimal

@Entity(
    tableName = "service_catalog",
    indices = [Index("name")]
)
data class ServiceCatalogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String?,
    val basePrice: BigDecimal?,
    val kind: ItemKind
)
