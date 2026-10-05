package edu.unicauca.aplimovil.proyectogasti.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.GastoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.GastoEntity
import edu.unicauca.aplimovil.proyectogasti.data.repository.PresupuestoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Estado de la pantalla principal (Home).
 * Contiene los totales financieros (presupuesto y gastos) y el listado de gastos recientes.
 */
data class HomeUiState(
    val presupuestoTotal: Double = 0.0,
    val totalGastado: Double = 0.0,
    val listaGastos: List<GastoEntity> = emptyList()
) {
    /** Calcula el valor del presupuesto aún disponible para gasto. */
    val disponible: Double get() = presupuestoTotal - totalGastado
}

/**
 * ViewModel para la pantalla de inicio (HomeScreen).
 * Proporciona a la interfaz un resumen consolidado del saldo disponible, presupuesto total y gastos recientes.
 */
class HomeViewModel(
    presupuestoRepository: PresupuestoRepository,
    private val gastoDao: GastoDao,
    correoUsuario: String,
    mesAnio: String
) : ViewModel() {

    /**
     * Flujo reactivo que combina los flujos del DAO y Repositorio:
     * - Suma de presupuestos del mes.
     * - Suma total de egresos registrados.
     * - Listado completo de gastos del usuario.
     */
    val uiState: StateFlow<HomeUiState> = combine(
        presupuestoRepository.obtenerSumaPresupuestos(correoUsuario, mesAnio),
        gastoDao.obtenerTotalGastado(correoUsuario),
        gastoDao.obtenerGastosPorUsuario(correoUsuario)
    ) { sumaPresupuestos, totalGastado, gastos ->
        HomeUiState(
            presupuestoTotal = sumaPresupuestos ?: 0.0,
            totalGastado = totalGastado ?: 0.0,
            listaGastos = gastos
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )

    /** Elimina un gasto seleccionado desde el listado de la pantalla principal. */
    fun eliminarGasto(gasto: GastoEntity) {
        viewModelScope.launch {
            gastoDao.eliminarGasto(gasto)
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
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(presupuestoRepository, gastoDao, correoUsuario, mesAnio) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
