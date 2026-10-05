package edu.unicauca.aplimovil.proyectogasti.data.repository

import edu.unicauca.aplimovil.proyectogasti.data.local.dao.IngresoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.IngresoEntity
import kotlinx.coroutines.flow.Flow

class IngresoRepository(
    private val ingresoDao: IngresoDao
) {

    fun obtenerIngresos(
        correoUsuario: String
    ): Flow<List<IngresoEntity>> {
        return ingresoDao.obtenerIngresosPorUsuario(correoUsuario)
    }

    fun obtenerTotalIngresos(
        correoUsuario: String
    ): Flow<Double?> {
        return ingresoDao.obtenerTotalIngresos(correoUsuario)
    }

    suspend fun insertarIngreso(
        ingreso: IngresoEntity
    ) {
        ingresoDao.insertarIngreso(ingreso)
    }

    suspend fun eliminarIngreso(
        ingreso: IngresoEntity
    ) {
        ingresoDao.eliminarIngreso(ingreso)
    }
}