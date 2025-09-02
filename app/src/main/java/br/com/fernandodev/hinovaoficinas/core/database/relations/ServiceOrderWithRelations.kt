package br.com.fernandodev.hinovaoficinas.core.database.relations


import androidx.room.Embedded
import androidx.room.Relation
import br.com.fernandodev.hinovaoficinas.core.database.entities.CheckinPhotoEntity
import br.com.fernandodev.hinovaoficinas.core.database.entities.PaymentEntity
import br.com.fernandodev.hinovaoficinas.core.database.entities.ServiceOrderEntity
import br.com.fernandodev.hinovaoficinas.core.database.entities.ServiceOrderItemEntity
import br.com.fernandodev.hinovaoficinas.model.*

data class ServiceOrderWithRelations(
    @Embedded val order: ServiceOrderEntity,
    @Relation(parentColumn = "customerId", entityColumn = "id")
    val customer: CustomerEntity,
    @Relation(parentColumn = "vehicleId", entityColumn = "id")
    val vehicle: VehicleEntity,
    @Relation(parentColumn = "id", entityColumn = "osId")
    val items: List<ServiceOrderItemEntity>,
    @Relation(parentColumn = "id", entityColumn = "osId")
    val payments: List<PaymentEntity>,
    @Relation(parentColumn = "id", entityColumn = "osId")
    val photos: List<CheckinPhotoEntity>
)
