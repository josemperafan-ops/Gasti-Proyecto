package edu.unicauca.aplimovil.proyectogasti.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import edu.unicauca.aplimovil.proyectogasti.data.local.GastiDatabase
import edu.unicauca.aplimovil.proyectogasti.data.repository.PresupuestoRepository
import edu.unicauca.aplimovil.proyectogasti.ui.viewmodel.EstadisticasViewModel
import edu.unicauca.aplimovil.proyectogasti.ui.viewmodel.PeriodoEstadisticas
// import edu.unicauca.aplimovil.proyectogasti.util.obtenerMesAnioActual

private val FondoStats = Color(0xFF050B14)
private val AzulStats = Color(0xFF101A2A)
private val VerdeStats = Color(0xFF00E5A8)
private val GrisStats = Color(0xFF8B9AAF)
private val BordeStats = Color(0xFF26344A)
private val AmarilloStats = Color(0xFFFFC857)
private val AzulBarra = Color(0xFF14253A)
private val FondoBarra = Color(0xFF0A1423)


@Composable
fun EstadisticasScreen(
    onBack: () -> Unit,
    onInicio: () -> Unit,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit
) {

    // ---------------------------------------------------------
    // BASE DE DATOS
    // ---------------------------------------------------------

    val context =
        androidx.compose.ui.platform.LocalContext.current

    val database = remember {
        GastiDatabase.getDatabase(
            context.applicationContext
        )
    }

    val presupuestoRepository = remember {
        PresupuestoRepository(
            database.presupuestoDao()
        )
    }

    // ---------------------------------------------------------
    // USUARIO ACTUAL
    // ---------------------------------------------------------

    val correoUsuario =
        FirebaseAuth
            .getInstance()
            .currentUser
            ?.email
            ?: ""

    // ---------------------------------------------------------
    // MES ACTUAL
    // ---------------------------------------------------------

    val calendario = java.util.Calendar.getInstance()

    val meses = listOf(
        "Enero",
        "Febrero",
        "Marzo",
        "Abril",
        "Mayo",
        "Junio",
        "Julio",
        "Agosto",
        "Septiembre",
        "Octubre",
        "Noviembre",
        "Diciembre"
    )

    val mesActual = meses[
        calendario.get(java.util.Calendar.MONTH)
    ]

    val anioActual = calendario.get(
        java.util.Calendar.YEAR
    )

    val mesAnio = "$mesActual $anioActual"

    // ---------------------------------------------------------
    // VIEWMODEL
    // ---------------------------------------------------------
    val viewModel: EstadisticasViewModel =
        viewModel(
            factory = EstadisticasViewModel.Factory(
                gastoDao = database.gastoDao(),
                presupuestoRepository = presupuestoRepository,
                correoUsuario = correoUsuario,
                mesAnio = mesAnio
            )
        )


    val uiState by
    viewModel.uiState.collectAsState()

    // ---------------------------------------------------------
    // PANTALLA
    // ---------------------------------------------------------

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoStats)
    ) {

        // -----------------------------------------------------
        // CONTENIDO PRINCIPAL
        // -----------------------------------------------------

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 55.dp,
                    bottom = 75.dp
                )
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            // -------------------------------------------------
            // ENCABEZADO
            // -------------------------------------------------

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 13.dp,
                        vertical = 14.dp
                    )
            ) {

                Text(
                    text = "Estadísticas",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                // ---------------------------------------------
                // SELECTOR SEMANA / MES / AÑO
                // ---------------------------------------------

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(7.dp)
                ) {

                    PeriodoStats(
                        texto = "Semana",
                        seleccionado =
                            uiState.periodoSeleccionado ==
                                    PeriodoEstadisticas.SEMANA,
                        modifier =
                            Modifier.weight(1f),
                        onClick = {
                            viewModel.seleccionarPeriodo(
                                PeriodoEstadisticas.SEMANA
                            )
                        }
                    )

                    PeriodoStats(
                        texto = "Mes",
                        seleccionado =
                            uiState.periodoSeleccionado ==
                                    PeriodoEstadisticas.MES,
                        modifier =
                            Modifier.weight(1f),
                        onClick = {
                            viewModel.seleccionarPeriodo(
                                PeriodoEstadisticas.MES
                            )
                        }
                    )

                    PeriodoStats(
                        texto = "Año",
                        seleccionado =
                            uiState.periodoSeleccionado ==
                                    PeriodoEstadisticas.ANIO,
                        modifier =
                            Modifier.weight(1f),
                        onClick = {
                            viewModel.seleccionarPeriodo(
                                PeriodoEstadisticas.ANIO
                            )
                        }
                    )
                }
            }

            // -------------------------------------------------
            // RESUMEN
            // -------------------------------------------------

            TarjetaStats(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
            ) {

                Text(
                    text = "Resumen",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text =
                        "Total gastado: $ ${
                            formatoDinero(
                                uiState.totalGastado
                            )
                        }",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text =
                        if (
                            uiState.presupuestoTotal > 0
                        ) {
                            "Presupuesto: $ ${
                                formatoDinero(
                                    uiState.presupuestoTotal
                                )
                            }"
                        } else {
                            "Sin presupuesto registrado"
                        },
                    color = GrisStats,
                    fontSize = 10.sp
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text =
                        "Uso del presupuesto: ${
                            uiState.porcentajePresupuesto
                                .toInt()
                        }%",
                    color =
                        if (
                            uiState.porcentajePresupuesto >= 100
                        ) {
                            AmarilloStats
                        } else {
                            VerdeStats
                        },
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                // Barra de progreso del presupuesto

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(
                            Color(0xFF182538),
                            RoundedCornerShape(4.dp)
                        )
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(
                                (
                                        uiState
                                            .porcentajePresupuesto
                                                / 100f
                                        ).coerceIn(
                                        0f,
                                        1f
                                    )
                            )
                            .height(6.dp)
                            .background(
                                if (
                                    uiState
                                        .porcentajePresupuesto >=
                                    100
                                ) {
                                    AmarilloStats
                                } else {
                                    VerdeStats
                                },
                                RoundedCornerShape(4.dp)
                            )
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // -------------------------------------------------
            // GASTO POR DÍA
            // -------------------------------------------------

            TarjetaStats(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
            ) {

                Text(
                    text = "Gasto por día",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text =
                        "${uiState.periodoSeleccionado.titulo} · Total $ ${
                            formatoDinero(
                                uiState.totalGastado
                            )
                        }",
                    color = GrisStats,
                    fontSize = 9.sp
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                GraficoGastoDiario(
                    datos = uiState.graficaDiaria
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // -------------------------------------------------
            // POR CATEGORÍA
            // -------------------------------------------------

            TarjetaStats(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
            ) {

                Text(
                    text = "Por categoría",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Distribución de tus gastos",
                    color = GrisStats,
                    fontSize = 9.sp
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                if (
                    uiState.gastosPorCategoria.isEmpty()
                ) {

                    Text(
                        text =
                            "No hay gastos registrados en este periodo.",
                        color = GrisStats,
                        fontSize = 10.sp
                    )

                } else {

                    uiState.gastosPorCategoria
                        .forEach { categoria ->

                            val porcentaje =
                                if (
                                    uiState.totalGastado > 0
                                ) {
                                    (
                                            categoria.monto /
                                                    uiState.totalGastado
                                            ).toFloat()
                                } else {
                                    0f
                                }

                            CategoriaStats(
                                nombre =
                                    categoria.nombre,

                                monto =
                                    "$ ${
                                        formatoDinero(
                                            categoria.monto
                                        )
                                    }",

                                porcentaje =
                                    "${
                                        (
                                                porcentaje * 100
                                                ).toInt()
                                    }%",

                                progreso =
                                    porcentaje,

                                simbolo =
                                    obtenerSimboloCategoria(
                                        categoria.nombre
                                    )
                            )
                        }
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // -------------------------------------------------
            // COMPARACIÓN MENSUAL
            // -------------------------------------------------

            TarjetaStats(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
            ) {

                Text(
                    text = "Comparación mensual",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Últimos 4 meses",
                    color = GrisStats,
                    fontSize = 9.sp
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                GraficoComparativaMensual(
                    datos = uiState.graficaMensual
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // -------------------------------------------------
            // INSIGHT
            // -------------------------------------------------

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
                    .background(
                        Color(0xFF00352D),
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        1.dp,
                        Color(0xFF006B59),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(13.dp)
            ) {

                Column {

                    Text(
                        text = "💡 Insight del periodo",
                        color = VerdeStats,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    val mayorCategoria =
                        uiState.mayorCategoria

                    if (
                        mayorCategoria != null
                    ) {

                        val porcentajeMayor =
                            if (
                                uiState.totalGastado > 0
                            ) {
                                (
                                        mayorCategoria.monto /
                                                uiState.totalGastado
                                        ) * 100
                            } else {
                                0.0
                            }

                        Text(
                            text =
                                "Tu mayor gasto es " +
                                        "${mayorCategoria.nombre} " +
                                        "con $ " +
                                        formatoDinero(
                                            mayorCategoria.monto
                                        ) +
                                        " (${porcentajeMayor.toInt()}% del total).",

                            color = Color(0xFFB5EBDD),
                            fontSize = 10.sp
                        )

                    } else {

                        Text(
                            text =
                                "Todavía no hay suficientes datos " +
                                        "para generar un insight.",

                            color = Color(0xFFB5EBDD),
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(15.dp)
            )
        }

        // -----------------------------------------------------
        // BARRA INFERIOR
        // -----------------------------------------------------

        BarraInferiorStats(
            modifier = Modifier
                .fillMaxWidth()
                .align(
                    Alignment.BottomCenter
                ),

            onInicio = onInicio,
            onGastos = onGastos,
            onPresupuesto = onPresupuesto,
            onStats = onStats,
            onPerfil = onPerfil
        )
    }
}


// =============================================================
// TARJETA
// =============================================================

@Composable
private fun TarjetaStats(
    modifier: Modifier = Modifier,
    contenido: @Composable () -> Unit
) {

    Column(
        modifier = modifier
            .background(
                AzulStats,
                RoundedCornerShape(15.dp)
            )
            .border(
                1.dp,
                BordeStats,
                RoundedCornerShape(15.dp)
            )
            .padding(13.dp)
    ) {

        contenido()
    }
}


// =============================================================
// SELECTOR DE PERIODO
// =============================================================

@Composable
private fun PeriodoStats(
    texto: String,
    seleccionado: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .height(32.dp)
            .background(
                if (
                    seleccionado
                ) {
                    VerdeStats
                } else {
                    AzulStats
                },
                RoundedCornerShape(9.dp)
            )
            .border(
                1.dp,
                if (
                    seleccionado
                ) {
                    VerdeStats
                } else {
                    BordeStats
                },
                RoundedCornerShape(9.dp)
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = texto,
            color =
                if (
                    seleccionado
                ) {
                    Color(0xFF00150F)
                } else {
                    GrisStats
                },
            fontSize = 10.sp,
            fontWeight =
                if (
                    seleccionado
                ) {
                    FontWeight.Bold
                } else {
                    FontWeight.Normal
                }
        )
    }
}


// =============================================================
// GRÁFICO GASTO DIARIO
// =============================================================

@Composable
private fun GraficoGastoDiario(
    datos:
    List<
            edu.unicauca.aplimovil.proyectogasti
            .ui.viewmodel.PuntoGraficaDiaria
            >
) {

    if (datos.isEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "No hay datos para mostrar.",
                color = GrisStats,
                fontSize = 9.sp
            )
        }

        return
    }

    val maximo =
        datos.maxOfOrNull {
            it.monto
        } ?: 0.0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp),
        horizontalArrangement =
            Arrangement.SpaceEvenly,
        verticalAlignment =
            Alignment.Bottom
    ) {

        datos.forEach { punto ->

            Column(
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .width(25.dp)
                        .height(90.dp),
                    contentAlignment =
                        Alignment.BottomCenter
                ) {

                    val altura =
                        if (
                            maximo > 0
                        ) {
                            (
                                    punto.monto /
                                            maximo
                                    ).toFloat()
                                .coerceIn(
                                    0f,
                                    1f
                                )
                        } else {
                            0f
                        }

                    Box(
                        modifier = Modifier
                            .width(22.dp)
                            .fillMaxWidth()
                            .height(
                                (
                                        altura * 75f
                                        ).dp
                            )
                            .background(
                                if (
                                    punto.monto ==
                                    maximo &&
                                    maximo > 0
                                ) {
                                    AmarilloStats
                                } else {
                                    VerdeStats
                                },
                                RoundedCornerShape(6.dp)
                            )
                    )
                }

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = punto.etiqueta,
                    color = GrisStats,
                    fontSize = 8.sp
                )
            }
        }
    }
}


// =============================================================
// CATEGORÍA
// =============================================================

@Composable
private fun CategoriaStats(
    nombre: String,
    monto: String,
    porcentaje: String,
    progreso: Float,
    simbolo: String
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier.size(25.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = simbolo,
                    fontSize = 11.sp
                )
            }

            Spacer(
                modifier = Modifier.width(5.dp)
            )

            Text(
                text = nombre,
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = monto,
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.width(5.dp)
            )

            Text(
                text = porcentaje,
                color = GrisStats,
                fontSize = 8.sp
            )
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(
                    Color(0xFF182538),
                    RoundedCornerShape(3.dp)
                )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(
                        progreso.coerceIn(
                            0f,
                            1f
                        )
                    )
                    .height(5.dp)
                    .background(
                        VerdeStats,
                        RoundedCornerShape(3.dp)
                    )
            )
        }

        Spacer(
            modifier = Modifier.height(9.dp)
        )
    }
}


// =============================================================
// GRÁFICO COMPARATIVA MENSUAL
// =============================================================

@Composable
private fun GraficoComparativaMensual(
    datos:
    List<
            edu.unicauca.aplimovil.proyectogasti
            .ui.viewmodel.PuntoGraficaMensual
            >
) {

    if (datos.isEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            contentAlignment =
                Alignment.Center
        ) {

            Text(
                text = "No hay datos para mostrar.",
                color = GrisStats,
                fontSize = 9.sp
            )
        }

        return
    }

    val maximo =
        datos.maxOfOrNull {
            it.monto
        } ?: 0.0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp),
        horizontalArrangement =
            Arrangement.SpaceEvenly,
        verticalAlignment =
            Alignment.Bottom
    ) {

        datos.forEachIndexed { indice, punto ->

            Column(
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .width(28.dp)
                        .height(90.dp),
                    contentAlignment =
                        Alignment.BottomCenter
                ) {

                    val altura =
                        if (
                            maximo > 0
                        ) {
                            (
                                    punto.monto /
                                            maximo
                                    ).toFloat()
                                .coerceIn(
                                    0f,
                                    1f
                                )
                        } else {
                            0f
                        }

                    Box(
                        modifier = Modifier
                            .width(23.dp)
                            .height(
                                (
                                        altura * 75f
                                        ).dp
                            )
                            .background(
                                if (
                                    indice ==
                                    datos.lastIndex
                                ) {
                                    VerdeStats
                                } else {
                                    AzulBarra
                                },
                                RoundedCornerShape(6.dp)
                            )
                    )
                }

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = punto.etiqueta,
                    color = GrisStats,
                    fontSize = 8.sp
                )
            }
        }
    }
}


// =============================================================
// SÍMBOLO DE CATEGORÍA
// =============================================================

private fun obtenerSimboloCategoria(
    categoria: String
): String {

    return when (
        categoria.trim().lowercase()
    ) {

        "comida" ->
            "🍔"

        "alimentación" ->
            "🍔"

        "transporte" ->
            "🚌"

        "estudio" ->
            "📚"

        "educación" ->
            "📚"

        "entretenimiento" ->
            "🎮"

        "compras" ->
            "🛍"

        "salud" ->
            "💊"

        "hogar" ->
            "🏠"

        "servicios" ->
            "💡"

        else ->
            "📦"
    }
}


// =============================================================
// FORMATO DINERO
// =============================================================

private fun formatoDinero(
    valor: Double
): String {

    return String.format(
        "%,.0f",
        valor
    )
}


// =============================================================
// BARRA INFERIOR
// =============================================================

@Composable
private fun BarraInferiorStats(
    modifier: Modifier = Modifier,
    onInicio: () -> Unit,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit
) {

    Box(
        modifier = modifier
            .height(68.dp)
            .background(
                FondoBarra
            )
            .border(
                1.dp,
                BordeStats
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 7.dp,
                    vertical = 7.dp
                ),
            horizontalArrangement =
                Arrangement.SpaceAround
        ) {

            ItemBarraStats(
                simbolo = "⌂",
                texto = "Inicio",
                activo = false,
                onClick = onInicio
            )

            ItemBarraStats(
                simbolo = "+",
                texto = "Gastos",
                activo = false,
                onClick = onGastos
            )

            ItemBarraStats(
                simbolo = "$",
                texto = "Presupuesto",
                activo = false,
                onClick = onPresupuesto
            )

            ItemBarraStats(
                simbolo = "▥",
                texto = "Stats",
                activo = true,
                onClick = onStats
            )

            ItemBarraStats(
                simbolo = "♙",
                texto = "Perfil",
                activo = false,
                onClick = onPerfil
            )
        }
    }
}


// =============================================================
// ITEM BARRA INFERIOR
// =============================================================

@Composable
private fun ItemBarraStats(
    simbolo: String,
    texto: String,
    activo: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .width(60.dp)
            .clickable {
                onClick()
            },
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text = simbolo,
            color =
                if (activo) {
                    VerdeStats
                } else {
                    GrisStats
                },
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = texto,
            color =
                if (activo) {
                    VerdeStats
                } else {
                    GrisStats
                },
            fontSize = 8.sp
        )
    }
}