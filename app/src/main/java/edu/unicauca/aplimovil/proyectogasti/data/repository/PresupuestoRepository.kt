package edu.unicauca.aplimovil.proyectogasti.data.repository

import edu.unicauca.aplimovil.proyectogasti.data.local.dao.PresupuestoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.PresupuestoEntity
import kotlinx.coroutines.flow.Flow

class PresupuestoRepository(private val presupuestoDao: PresupuestoDao) {
    suspend fun guardarPresupuesto(presupuesto: PresupuestoEntity) = presupuestoDao.guardarPresupuesto(presupuesto)

    fun obtenerPresupuestos(correo: String, mesAnio: String): Flow<List<PresupuestoEntity>> =
        presupuestoDao.obtenerPresupuestosPorUsuario(correo, mesAnio)

    fun obtenerSumaPresupuestos(correo: String, mesAnio: String): Flow<Double?> =
        presupuestoDao.obtenerSumaPresupuestos(correo, mesAnio)

    suspend fun eliminarPresupuesto(presupuesto: PresupuestoEntity) = presupuestoDao.eliminarPresupuesto(presupuesto)
}
