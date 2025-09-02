package br.com.fernandodev.hinovaoficinas.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.fernandodev.hinovaoficinas.core.database.dao.CheckinPhotoDao
import br.com.fernandodev.hinovaoficinas.core.database.dao.PaymentDao
import br.com.fernandodev.hinovaoficinas.core.database.dao.ServiceOrderDao
import br.com.fernandodev.hinovaoficinas.core.database.dao.ServiceOrderItemDao
import br.com.fernandodev.hinovaoficinas.core.database.entities.CheckinPhotoEntity
import br.com.fernandodev.hinovaoficinas.core.database.entities.PaymentEntity
import br.com.fernandodev.hinovaoficinas.core.database.entities.ServiceOrderEntity
import br.com.fernandodev.hinovaoficinas.core.database.entities.ServiceOrderItemEntity

@Database(
    entities = [
        ServiceOrderEntity::class,
        ServiceOrderItemEntity::class,
        PaymentEntity::class,
        CheckinPhotoEntity::class

    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun serviceOrderDao(): ServiceOrderDao
    abstract fun serviceOrderItemDao(): ServiceOrderItemDao
    abstract fun paymentDao(): PaymentDao

    abstract fun checkinPhotoDao(): CheckinPhotoDao
}
