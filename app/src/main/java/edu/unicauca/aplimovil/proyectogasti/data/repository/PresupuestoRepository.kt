package edu.unicauca.aplimovil.proyectogasti.data.repository

import edu.unicauca.aplimovil.proyectogasti.data.local.dao.PresupuestoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.PresupuestoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio encargado de gestionar la lógica de acceso a datos de presupuestos.
 * Abstrae las llamadas a la base de datos a través de PresupuestoDao.
 */
class PresupuestoRepository(private val presupuestoDao: PresupuestoDao) {
    /** Guarda un nuevo presupuesto o actualiza uno existente. */
    suspend fun guardarPresupuesto(presupuesto: PresupuestoEntity) = presupuestoDao.guardarPresupuesto(presupuesto)

    /** Obtiene la lista de presupuestos asignados a un usuario para un determinado mes y año. */
    fun obtenerPresupuestos(correo: String, mesAnio: String): Flow<List<PresupuestoEntity>> =
        presupuestoDao.obtenerPresupuestosPorUsuario(correo, mesAnio)

    /** Obtiene la suma total de los presupuestos configurados en el mes actual. */
    fun obtenerSumaPresupuestos(correo: String, mesAnio: String): Flow<Double?> =
        presupuestoDao.obtenerSumaPresupuestos(correo, mesAnio)

    /** Elimina un presupuesto especificado. */
    suspend fun eliminarPresupuesto(presupuesto: PresupuestoEntity) = presupuestoDao.eliminarPresupuesto(presupuesto)
}
