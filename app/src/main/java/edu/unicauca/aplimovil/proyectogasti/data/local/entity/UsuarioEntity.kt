package edu.unicauca.aplimovil.proyectogasti.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey
    val correo: String,
    val nombre: String
)
