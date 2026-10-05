package edu.unicauca.aplimovil.proyectogasti.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "presupuestos")
data class PresupuestoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val correoUsuario: String,
    val nombre: String = "Presupuesto General",
    val mesAnio: String,
    val montoTotal: Double
)
