package edu.unicauca.aplimovil.proyectogasti.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa la tabla de presupuestos en la base de datos local Room.
 * Almacena el monto límite o plan de gasto establecido por el usuario para un periodo determinado.
 */
@Entity(tableName = "presupuestos")
data class PresupuestoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val correoUsuario: String,
    val nombre: String = "Presupuesto General",
    val mesAnio: String,
    val montoTotal: Double
)
