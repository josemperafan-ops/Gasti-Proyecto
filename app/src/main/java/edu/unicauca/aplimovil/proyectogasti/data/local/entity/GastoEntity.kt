package edu.unicauca.aplimovil.proyectogasti.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa la tabla de gastos en la base de datos local Room.
 * Registra cada egreso individual realizado por un usuario.
 */
@Entity(tableName = "gastos")
data class GastoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val correoUsuario: String,
    val monto: Double,
    val categoria: String,
    val descripcion: String,
    val fecha: String
)
