package edu.unicauca.aplimovil.proyectogasti.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.IngresoEntity
import edu.unicauca.aplimovil.proyectogasti.data.repository.IngresoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IngresoViewModel(
    private val repository: IngresoRepository,
    private val correoUsuario: String
) : ViewModel() {

    // ---------------------------------------------------------
    // LISTA DE INGRESOS
    // ---------------------------------------------------------

    val ingresos: StateFlow<List<IngresoEntity>> =
        repository.obtenerIngresos(correoUsuario)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )


    // ---------------------------------------------------------
    // TOTAL DE INGRESOS
    // ---------------------------------------------------------

    val totalIngresos: StateFlow<Double> =
        repository.obtenerTotalIngresos(correoUsuario)
            .map { total ->
                total ?: 0.0
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0.0
            )


    // ---------------------------------------------------------
    // REGISTRAR INGRESO
    // ---------------------------------------------------------

    fun registrarIngreso(
        monto: Double,
        descripcion: String,
        fecha: String
    ) {

        if (monto <= 0) return

        viewModelScope.launch {

            repository.insertarIngreso(
                IngresoEntity(
                    correoUsuario = correoUsuario,
                    monto = monto,
                    descripcion = descripcion,
                    fecha = fecha
                )
            )
        }
    }


    // ---------------------------------------------------------
    // ELIMINAR INGRESO
    // ---------------------------------------------------------

    fun eliminarIngreso(
        ingreso: IngresoEntity
    ) {

        viewModelScope.launch {

            repository.eliminarIngreso(ingreso)
        }
    }


    // ---------------------------------------------------------
    // FACTORY
    // ---------------------------------------------------------

    class Factory(
        private val repository: IngresoRepository,
        private val correoUsuario: String
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {

            if (modelClass.isAssignableFrom(IngresoViewModel::class.java)) {

                return IngresoViewModel(
                    repository = repository,
                    correoUsuario = correoUsuario
                ) as T
            }

            throw IllegalArgumentException(
                "Unknown ViewModel class"
            )
        }
    }
}