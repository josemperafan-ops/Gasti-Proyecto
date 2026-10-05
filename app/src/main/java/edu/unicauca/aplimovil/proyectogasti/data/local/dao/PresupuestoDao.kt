package edu.unicauca.aplimovil.proyectogasti.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.PresupuestoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz Data Access Object (DAO) para gestionar las operaciones de base de datos de Presupuestos.
 */
@Dao
interface PresupuestoDao {
    /** Guarda o reemplaza una entidad de presupuesto en la base de datos. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarPresupuesto(presupuesto: PresupuestoEntity)

    /** Consulta en tiempo real la lista de presupuestos creados para un usuario y mes determinado. */
    @Query("SELECT * FROM presupuestos WHERE correoUsuario = :correo AND mesAnio = :mesAnio")
    fun obtenerPresupuestosPorUsuario(correo: String, mesAnio: String): Flow<List<PresupuestoEntity>>

    /** Calcula el flujo con la suma total de los presupuestos configurados para un mes. */
    @Query("SELECT SUM(montoTotal) FROM presupuestos WHERE correoUsuario = :correo AND mesAnio = :mesAnio")
    fun obtenerSumaPresupuestos(correo: String, mesAnio: String): Flow<Double?>

    /** Obtiene de forma directa y suspendida la suma de presupuestos para validación previa al registro de un gasto. */
    @Query("SELECT SUM(montoTotal) FROM presupuestos WHERE correoUsuario = :correo AND mesAnio = :mesAnio")
    suspend fun obtenerSumaPresupuestosSuspend(correo: String, mesAnio: String): Double?

    /** Elimina un presupuesto específico de la base de datos. */
    @Delete
    suspend fun eliminarPresupuesto(presupuesto: PresupuestoEntity)
}
