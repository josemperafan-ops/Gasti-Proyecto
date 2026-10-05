package edu.unicauca.aplimovil.proyectogasti.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.GastoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.PresupuestoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.GastoEntity
import edu.unicauca.aplimovil.proyectogasti.data.repository.GastoRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class RegistrarGastoViewModel(
    private val repository: GastoRepository,
    private val presupuestoDao: PresupuestoDao,
    private val gastoDao: GastoDao,
    private val correoUsuario: String,
    private val mesAnio: String
) : ViewModel() {

    private val _mensajeError = MutableStateFlow("")
    val mensajeError: StateFlow<String> = _mensajeError.asStateFlow()

    private val _navegarInicio = MutableSharedFlow<Unit>()
    val navegarInicio: SharedFlow<Unit> = _navegarInicio.asSharedFlow()

    fun limpiarError() {
        _mensajeError.value = ""
    }

    private fun parsearFecha(fechaStr: String): LocalDate? {
        return try {
            val texto = fechaStr.trim().lowercase(java.util.Locale.forLanguageTag("es"))
            val partes = texto.split(" de ")
            if (partes.size == 3) {
                val dia = partes[0].trim().toIntOrNull() ?: return null
                val mesTexto = partes[1].trim()
                val anio = partes[2].trim().toIntOrNull() ?: return null

                val mesesMap = mapOf(
                    "enero" to 1, "febrero" to 2, "marzo" to 3, "abril" to 4,
                    "mayo" to 5, "junio" to 6, "julio" to 7, "agosto" to 8,
                    "septiembre" to 9, "octubre" to 10, "noviembre" to 11, "diciembre" to 12
                )
                val mes = mesesMap[mesTexto] ?: return null
                LocalDate.of(anio, mes, dia)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun esFechaValida(fechaStr: String): Boolean {
        if (fechaStr.isBlank()) return false
        return parsearFecha(fechaStr) != null
    }

    private fun obtenerErrorFecha(fechaStr: String): String {
        if (fechaStr.isBlank()) return "La fecha es obligatoria."
        return "Ingresa una fecha válida."
    }

    fun guardarGasto(id: Long = 0L, montoStr: String, categoria: String, descripcion: String, fecha: String) {
        val monto = montoStr.toDoubleOrNull()

        when {
            montoStr.isBlank() || monto == null -> {
                _mensajeError.value = "El monto es obligatorio."
            }
            monto <= 0.0 -> {
                _mensajeError.value = "El monto debe ser mayor que cero."
            }
            categoria.isBlank() -> {
                _mensajeError.value = "Debe seleccionar una categoría válida."
            }
            descripcion.isBlank() -> {
                _mensajeError.value = "La descripción es obligatoria."
            }
            !esFechaValida(fecha) -> {
                _mensajeError.value = obtenerErrorFecha(fecha)
            }
            else -> {
                viewModelScope.launch {
                    val presupuestoTotal = presupuestoDao.obtenerSumaPresupuestosSuspend(correoUsuario, mesAnio) ?: 0.0
                    val totalGastado = gastoDao.obtenerTotalGastadoSuspend(correoUsuario) ?: 0.0
                    val disponible = presupuestoTotal - totalGastado

                    if (presupuestoTotal <= 0.0) {
                        _mensajeError.value = "No puedes registrar gastos sin haber creado un presupuesto."
                    } else if (id == 0L && monto > disponible) {
                        _mensajeError.value = "El gasto excede tu presupuesto disponible ($${String.format("%,.0f", disponible)})."
                    } else {
                        val nuevoGasto = GastoEntity(
                            id = id,
                            correoUsuario = correoUsuario,
                            monto = monto,
                            categoria = categoria,
                            descripcion = descripcion.trim(),
                            fecha = fecha
                        )
                        repository.registrarGasto(nuevoGasto)
                        _navegarInicio.emit(Unit)
                    }
                }
            }
        }
    }

    class Factory(
        private val repository: GastoRepository,
        private val presupuestoDao: PresupuestoDao,
        private val gastoDao: GastoDao,
        private val correoUsuario: String,
        private val mesAnio: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegistrarGastoViewModel::class.java)) {
                return RegistrarGastoViewModel(repository, presupuestoDao, gastoDao, correoUsuario, mesAnio) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
