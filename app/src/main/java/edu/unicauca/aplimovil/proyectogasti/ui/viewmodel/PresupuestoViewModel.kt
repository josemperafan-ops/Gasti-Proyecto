package edu.unicauca.aplimovil.proyectogasti.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.GastoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.PresupuestoEntity
import edu.unicauca.aplimovil.proyectogasti.data.repository.PresupuestoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Estado de la interfaz de usuario para la pantalla de Presupuesto.
 * Calcula el saldo disponible y el porcentaje ejecutado del presupuesto.
 */
data class PresupuestoUiState(
    val listaPresupuestos: List<PresupuestoEntity> = emptyList(),
    val presupuestoTotal: Double = 0.0,
    val totalGastado: Double = 0.0
) {
    /** Saldo restante disponible (Suma Presupuestos - Total Gastado). */
    val disponible: Double get() = presupuestoTotal - totalGastado


    /** Proporción del presupuesto consumida (0.0 a 1.0+). */
    val porcentajeUsado: Float get() = if (presupuestoTotal > 0) (totalGastado / presupuestoTotal).toFloat() else 0f
}

/**
 * ViewModel encargado de la lógica de negocio para la gestión de presupuestos.
 * Combina en tiempo real los presupuestos creados con los gastos ejecutados.
 */
class PresupuestoViewModel(
    private val presupuestoRepository: PresupuestoRepository,
    private val gastoDao: GastoDao,
    private val correoUsuario: String,
    private val mesAnio: String
) : ViewModel() {

    /**
     * Flujo de estado de la UI. Combina:
     * 1. Lista de presupuestos creados para el mes/año actual.
     * 2. Suma acumulada de presupuestos.
     * 3. Total gastado en general por el usuario.
     */
    val uiState: StateFlow<PresupuestoUiState> = combine(
        presupuestoRepository.obtenerPresupuestos(correoUsuario, mesAnio),
        presupuestoRepository.obtenerSumaPresupuestos(correoUsuario, mesAnio),
        gastoDao.obtenerTotalGastado(correoUsuario)
    ) { lista, sumaTotal, totalGastado ->
        PresupuestoUiState(
            listaPresupuestos = lista,
            presupuestoTotal = sumaTotal ?: 0.0,
            totalGastado = totalGastado ?: 0.0
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PresupuestoUiState()
    )

    /** Crea un nuevo presupuesto o actualiza un presupuesto existente. */
    fun guardarPresupuesto(id: Long = 0L, nombre: String, monto: Double) {
        viewModelScope.launch {
            val presupuesto = PresupuestoEntity(
                id = id,
                correoUsuario = correoUsuario,
                nombre = if (nombre.isBlank()) "Presupuesto" else nombre,
                mesAnio = mesAnio,
                montoTotal = monto
            )
            presupuestoRepository.guardarPresupuesto(presupuesto)
        }
    }

    /** Elimina un presupuesto específico de la base de datos. */
    fun eliminarPresupuesto(presupuesto: PresupuestoEntity) {
        viewModelScope.launch {
            presupuestoRepository.eliminarPresupuesto(presupuesto)
        }
    }

    class Factory(
        private val presupuestoRepository: PresupuestoRepository,
        private val gastoDao: GastoDao,
        private val correoUsuario: String,
        private val mesAnio: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PresupuestoViewModel::class.java)) {
                return PresupuestoViewModel(presupuestoRepository, gastoDao, correoUsuario, mesAnio) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
