package edu.unicauca.aplimovil.proyectogasti.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.PresupuestoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PresupuestoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarPresupuesto(presupuesto: PresupuestoEntity)

    @Query("SELECT * FROM presupuestos WHERE correoUsuario = :correo AND mesAnio = :mesAnio")
    fun obtenerPresupuestosPorUsuario(correo: String, mesAnio: String): Flow<List<PresupuestoEntity>>

    @Query("SELECT SUM(montoTotal) FROM presupuestos WHERE correoUsuario = :correo AND mesAnio = :mesAnio")
    fun obtenerSumaPresupuestos(correo: String, mesAnio: String): Flow<Double?>

    @Query("SELECT SUM(montoTotal) FROM presupuestos WHERE correoUsuario = :correo AND mesAnio = :mesAnio")
    suspend fun obtenerSumaPresupuestosSuspend(correo: String, mesAnio: String): Double?

    @Delete
    suspend fun eliminarPresupuesto(presupuesto: PresupuestoEntity)
}
