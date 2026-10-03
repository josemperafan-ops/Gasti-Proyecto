package edu.unicauca.aplimovil.proyectogasti.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.IngresoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngresoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarIngreso(ingreso: IngresoEntity)

    @Query("""
        SELECT * FROM ingresos
        WHERE correoUsuario = :correo
        ORDER BY id DESC
    """)
    fun obtenerIngresosPorUsuario(
        correo: String
    ): Flow<List<IngresoEntity>>

    @Query("""
        SELECT SUM(monto) FROM ingresos
        WHERE correoUsuario = :correo
    """)
    fun obtenerTotalIngresos(
        correo: String
    ): Flow<Double?>

    @Delete
    suspend fun eliminarIngreso(
        ingreso: IngresoEntity
    )
}