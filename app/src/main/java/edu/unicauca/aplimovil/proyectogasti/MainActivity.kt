package edu.unicauca.aplimovil.proyectogasti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth

import edu.unicauca.aplimovil.proyectogasti.data.local.entity.GastoEntity
import edu.unicauca.aplimovil.proyectogasti.ui.theme.EstadisticasScreen
import edu.unicauca.aplimovil.proyectogasti.ui.theme.HomeScreen
import edu.unicauca.aplimovil.proyectogasti.ui.theme.LoginScreen
import edu.unicauca.aplimovil.proyectogasti.ui.theme.PerfilScreen
import edu.unicauca.aplimovil.proyectogasti.ui.theme.PresupuestoScreen
import edu.unicauca.aplimovil.proyectogasti.ui.theme.RegistrarGastoScreen
import edu.unicauca.aplimovil.proyectogasti.ui.theme.RegistrarIngresoScreen
import edu.unicauca.aplimovil.proyectogasti.ui.theme.RegistroScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val usuarioActual = FirebaseAuth
                .getInstance()
                .currentUser

            var pantallaActual by remember {
                mutableStateOf(
                    if (usuarioActual != null) {
                        "home"
                    } else {
                        "login"
                    }
                )
            }

            var gastoAEditar by remember {
                mutableStateOf<GastoEntity?>(null)
            }


            // =========================================================
            // LOGIN
            // =========================================================

            if (pantallaActual == "login") {

                LoginScreen(

                    onLogin = {
                        pantallaActual = "home"
                    },

                    onRegistro = {
                        pantallaActual = "registro"
                    }
                )
            }


            // =========================================================
            // REGISTRO DE USUARIO
            // =========================================================

            else if (pantallaActual == "registro") {

                RegistroScreen(

                    onRegistroExitoso = {
                        pantallaActual = "home"
                    },

                    onVolverLogin = {
                        pantallaActual = "login"
                    }
                )
            }


            // =========================================================
            // INICIO
            // =========================================================

            else if (pantallaActual == "home") {

                HomeScreen(

                    onRegistrarGasto = {
                        gastoAEditar = null
                        pantallaActual = "registrar_gasto"
                    },

                    onEditarGasto = { gasto ->
                        gastoAEditar = gasto
                        pantallaActual = "registrar_gasto"
                    },

                    onGastos = {
                        gastoAEditar = null
                        pantallaActual = "registrar_gasto"
                    },

                    onPresupuesto = {
                        pantallaActual = "presupuesto"
                    },

                    onStats = {
                        pantallaActual = "estadisticas"
                    },

                    onPerfil = {
                        pantallaActual = "perfil"
                    }
                )
            }


            // =========================================================
            // REGISTRAR GASTO
            // =========================================================

            else if (pantallaActual == "registrar_gasto") {

                RegistrarGastoScreen(

                    gastoAEditar = gastoAEditar,

                    onBack = {
                        gastoAEditar = null
                        pantallaActual = "home"
                    },

                    onInicio = {
                        gastoAEditar = null
                        pantallaActual = "home"
                    },

                    onGastos = {
                        gastoAEditar = null
                        pantallaActual = "registrar_gasto"
                    },

                    onPresupuesto = {
                        pantallaActual = "presupuesto"
                    },

                    onStats = {
                        pantallaActual = "estadisticas"
                    },

                    onPerfil = {
                        pantallaActual = "perfil"
                    }
                )
            }


            // =========================================================
            // REGISTRAR INGRESO
            // =========================================================

            else if (pantallaActual == "registrar_ingreso") {

                RegistrarIngresoScreen(

                    onVolver = {
                        pantallaActual = "perfil"
                    }
                )
            }


            // =========================================================
            // PRESUPUESTO
            // =========================================================

            else if (pantallaActual == "presupuesto") {

                PresupuestoScreen(

                    onBack = {
                        pantallaActual = "home"
                    },

                    onInicio = {
                        pantallaActual = "home"
                    },

                    onGastos = {
                        gastoAEditar = null
                        pantallaActual = "registrar_gasto"
                    },

                    onPresupuesto = {
                        pantallaActual = "presupuesto"
                    },

                    onStats = {
                        pantallaActual = "estadisticas"
                    },

                    onPerfil = {
                        pantallaActual = "perfil"
                    }
                )
            }


            // =========================================================
            // ESTADÍSTICAS
            // =========================================================

            else if (pantallaActual == "estadisticas") {

                EstadisticasScreen(

                    onBack = {
                        pantallaActual = "home"
                    },

                    onInicio = {
                        pantallaActual = "home"
                    },

                    onGastos = {
                        gastoAEditar = null
                        pantallaActual = "registrar_gasto"
                    },

                    onPresupuesto = {
                        pantallaActual = "presupuesto"
                    },

                    onStats = {
                        pantallaActual = "estadisticas"
                    },

                    onPerfil = {
                        pantallaActual = "perfil"
                    }
                )
            }


            // =========================================================
            // PERFIL
            // =========================================================

            else if (pantallaActual == "perfil") {

                PerfilScreen(

                    onInicio = {
                        pantallaActual = "home"
                    },

                    onGastos = {
                        gastoAEditar = null
                        pantallaActual = "registrar_gasto"
                    },

                    onPresupuesto = {
                        pantallaActual = "presupuesto"
                    },

                    onStats = {
                        pantallaActual = "estadisticas"
                    },
                    onPerfil = {
                        pantallaActual = "perfil"
                    },

                    onIngresos = {
                        pantallaActual = "registrar_ingreso"
                    },

                    onCerrarSesion = {
                        FirebaseAuth
                            .getInstance()
                            .signOut()

                        pantallaActual = "login"
                    }

                )
            }
        }
    }
}