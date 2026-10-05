package edu.unicauca.aplimovil.proyectogasti.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingresos")
data class IngresoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val correoUsuario: String,
    val monto: Double,
    val descripcion: String,
    val fecha: String
)