package edu.unicauca.aplimovil.proyectogasti.data.repository

import edu.unicauca.aplimovil.proyectogasti.data.local.dao.GastoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.GastoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio encargado de abstracción y gestión del acceso a datos de gastos.
 * Actúa como intermediario entre las fuentes de datos (GastoDao) y los ViewModels.
 */
class GastoRepository(private val gastoDao: GastoDao) {
    /** Registra o actualiza un gasto en la base de datos. */
    suspend fun registrarGasto(gasto: GastoEntity) = gastoDao.insertarGasto(gasto)

    /** Recupera el flujo reactivo con la lista de gastos del usuario. */
    fun obtenerGastos(correo: String): Flow<List<GastoEntity>> = gastoDao.obtenerGastosPorUsuario(correo)

    /** Retorna el flujo reactivo del total gastado por el usuario. */
    fun obtenerTotalGastado(correo: String): Flow<Double?> = gastoDao.obtenerTotalGastado(correo)

    /** Elimina un registro de gasto. */
    suspend fun eliminarGasto(gasto: GastoEntity) = gastoDao.eliminarGasto(gasto)
}
