package edu.unicauca.aplimovil.proyectogasti.data.repository

import edu.unicauca.aplimovil.proyectogasti.data.local.dao.GastoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.GastoEntity
import kotlinx.coroutines.flow.Flow

class GastoRepository(private val gastoDao: GastoDao) {
    suspend fun registrarGasto(gasto: GastoEntity) = gastoDao.insertarGasto(gasto)

    fun obtenerGastos(correo: String): Flow<List<GastoEntity>> = gastoDao.obtenerGastosPorUsuario(correo)

    fun obtenerTotalGastado(correo: String): Flow<Double?> = gastoDao.obtenerTotalGastado(correo)

    suspend fun eliminarGasto(gasto: GastoEntity) = gastoDao.eliminarGasto(gasto)
}
