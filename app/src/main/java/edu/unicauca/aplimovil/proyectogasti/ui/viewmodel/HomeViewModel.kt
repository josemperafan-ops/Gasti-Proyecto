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

data class HomeUiState(
    val presupuestoTotal: Double = 0.0,
    val totalGastado: Double = 0.0,
    val listaGastos: List<GastoEntity> = emptyList()
) {
    val disponible: Double get() = presupuestoTotal - totalGastado
}

class HomeViewModel(
    presupuestoRepository: PresupuestoRepository,
    private val gastoDao: GastoDao,
    correoUsuario: String,
    mesAnio: String
) : ViewModel() {

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
