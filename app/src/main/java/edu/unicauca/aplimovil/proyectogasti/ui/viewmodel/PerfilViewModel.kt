package edu.unicauca.aplimovil.proyectogasti.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.GastoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.UsuarioDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class PerfilUiState(
    val nombre: String = "Usuario",
    val correo: String = "",
    val cantidadGastos: Int = 0,
    val totalGastado: Double = 0.0,
    val diasUsando: Long = 0
)

class PerfilViewModel(
    usuarioDao: UsuarioDao,
    gastoDao: GastoDao,
    correoUsuario: String,
    diasUsando: Long
) : ViewModel() {

    val uiState: StateFlow<PerfilUiState> =
        combine(
            usuarioDao.obtenerUsuario(correoUsuario),
            gastoDao.obtenerGastosPorUsuario(correoUsuario)
        ) { usuario, gastos ->

            PerfilUiState(
                nombre = usuario?.nombre ?: "Usuario",
                correo = usuario?.correo ?: correoUsuario,
                cantidadGastos = gastos.size,
                totalGastado = gastos.sumOf { it.monto },
                diasUsando = diasUsando
            )

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PerfilUiState(
                correo = correoUsuario,
                diasUsando = diasUsando
            )
        )

    class Factory(
        private val usuarioDao: UsuarioDao,
        private val gastoDao: GastoDao,
        private val correoUsuario: String,
        private val diasUsando: Long
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {

            if (
                modelClass.isAssignableFrom(
                    PerfilViewModel::class.java
                )
            ) {
                return PerfilViewModel(
                    usuarioDao = usuarioDao,
                    gastoDao = gastoDao,
                    correoUsuario = correoUsuario,
                    diasUsando = diasUsando
                ) as T
            }

            throw IllegalArgumentException(
                "Unknown ViewModel class"
            )
        }
    }
}