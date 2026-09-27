package edu.unicauca.aplimovil.proyectogasti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import edu.unicauca.aplimovil.proyectogasti.ui.theme.EstadisticasScreen
import edu.unicauca.aplimovil.proyectogasti.ui.theme.HomeScreen
import edu.unicauca.aplimovil.proyectogasti.ui.theme.LoginScreen
import edu.unicauca.aplimovil.proyectogasti.ui.theme.PerfilScreen
import edu.unicauca.aplimovil.proyectogasti.ui.theme.PresupuestoScreen
import edu.unicauca.aplimovil.proyectogasti.ui.theme.RegistrarGastoScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var pantallaActual by remember {
                mutableStateOf("login")
            }

            /*
             * LOGIN
             */

            if (pantallaActual == "login") {

                LoginScreen(
                    onLogin = {
                        pantallaActual = "home"
                    }
                )
            }

            /*
             * =====================================================
             * INICIO
             * =====================================================
             */

            else if (pantallaActual == "home") {

                HomeScreen(
                    onRegistrarGasto = {
                        pantallaActual = "registrar_gasto"
                    },

                    onGastos = {
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

            /*
             * =====================================================
             * GASTOS
             * =====================================================
             */

            else if (pantallaActual == "registrar_gasto") {

                RegistrarGastoScreen(

                    onBack = {
                        pantallaActual = "home"
                    },

                    onInicio = {
                        pantallaActual = "home"
                    },

                    onGastos = {
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

            /*
             * =====================================================
             * PRESUPUESTO
             * =====================================================
             */

            else if (pantallaActual == "presupuesto") {

                PresupuestoScreen(

                    onBack = {
                        pantallaActual = "home"
                    },

                    onInicio = {
                        pantallaActual = "home"
                    },

                    onGastos = {
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

            /*
             * =====================================================
             * ESTADÍSTICAS
             * =====================================================
             */

            else if (pantallaActual == "estadisticas") {

                EstadisticasScreen(

                    onBack = {
                        pantallaActual = "home"
                    },

                    onInicio = {
                        pantallaActual = "home"
                    },

                    onGastos = {
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

            /*
             * =====================================================
             * PERFIL
             * =====================================================
             */

            else if (pantallaActual == "perfil") {

                PerfilScreen(

                    onInicio = {
                        pantallaActual = "home"
                    },

                    onGastos = {
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
        }
    }
}