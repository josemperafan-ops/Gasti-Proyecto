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

data class PresupuestoUiState(
    val listaPresupuestos: List<PresupuestoEntity> = emptyList(),
    val presupuestoTotal: Double = 0.0,
    val totalGastado: Double = 0.0
) {
    val disponible: Double get() = presupuestoTotal - totalGastado
    val porcentajeUsado: Float get() = if (presupuestoTotal > 0) (totalGastado / presupuestoTotal).toFloat() else 0f
}

class PresupuestoViewModel(
    private val presupuestoRepository: PresupuestoRepository,
    private val gastoDao: GastoDao,
    private val correoUsuario: String,
    private val mesAnio: String
) : ViewModel() {

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
