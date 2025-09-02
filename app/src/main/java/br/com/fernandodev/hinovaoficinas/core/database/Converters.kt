package br.com.fernandodev.hinovaoficinas.core.database

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter fun fromLongToDate(value: Long?): Date? = value?.let { Date(it) }
    @TypeConverter fun fromDateToLong(date: Date?): Long? = date?.time
}
