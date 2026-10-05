package edu.unicauca.aplimovil.proyectogasti.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.GastoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz Data Access Object (DAO) para gestionar las operaciones de base de datos de Gastos.
 */
@Dao
interface GastoDao {
    /** Inserta un nuevo gasto o actualiza uno existente en caso de conflicto de ID. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarGasto(gasto: GastoEntity)

    /** Obtiene el flujo de la lista de gastos registrados por un usuario en orden descendente. */
    @Query("SELECT * FROM gastos WHERE correoUsuario = :correo ORDER BY id DESC")
    fun obtenerGastosPorUsuario(correo: String): Flow<List<GastoEntity>>

    /** Obtiene el flujo del total acumulado en gastos para el usuario actual. */
    @Query("SELECT SUM(monto) FROM gastos WHERE correoUsuario = :correo")
    fun obtenerTotalGastado(correo: String): Flow<Double?>

    /** Consulta de forma suspendida el valor acumulado en gastos del usuario para validaciones. */
    @Query("SELECT SUM(monto) FROM gastos WHERE correoUsuario = :correo")
    suspend fun obtenerTotalGastadoSuspend(correo: String): Double?

    /** Elimina un registro de gasto específico de la base de datos. */
    @Delete
    suspend fun eliminarGasto(gasto: GastoEntity)
}
