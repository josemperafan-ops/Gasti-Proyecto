package edu.unicauca.aplimovil.proyectogasti.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.GastoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarGasto(gasto: GastoEntity)

    @Query("SELECT * FROM gastos WHERE correoUsuario = :correo ORDER BY id DESC")
    fun obtenerGastosPorUsuario(correo: String): Flow<List<GastoEntity>>

    @Query("SELECT SUM(monto) FROM gastos WHERE correoUsuario = :correo")
    fun obtenerTotalGastado(correo: String): Flow<Double?>

    @Query("SELECT SUM(monto) FROM gastos WHERE correoUsuario = :correo")
    suspend fun obtenerTotalGastadoSuspend(correo: String): Double?

    @Delete
    suspend fun eliminarGasto(gasto: GastoEntity)
}
