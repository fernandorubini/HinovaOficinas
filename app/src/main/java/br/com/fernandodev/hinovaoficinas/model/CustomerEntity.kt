package br.com.fernandodev.hinovaoficinas.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "customers",
    indices = [Index("name"), Index("doc")]
)
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val phone: String?,
    val email: String?,
    val doc: String?,
    val address: String?
)