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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val FondoStats = Color(0xFF050B14)
private val AzulStats = Color(0xFF101A2A)
private val VerdeStats = Color(0xFF00E5A8)
private val GrisStats = Color(0xFF8B9AAF)
private val BordeStats = Color(0xFF26344A)
private val AmarilloStats = Color(0xFFFFC857)

@Composable
fun EstadisticasScreen(
    onBack: () -> Unit,
    onInicio: () -> Unit,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoStats)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 55.dp,
                    bottom = 68.dp
                )
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {

                    PeriodoStats(
                        "Semana",
                        false,
                        Modifier.weight(1f)
                    )

                    PeriodoStats(
                        "Mes",
                        true,
                        Modifier.weight(1f)
                    )

                    PeriodoStats(
                        "Año",
                        false,
                        Modifier.weight(1f)
                    )
                }
            }

            TarjetaStats(
                Modifier
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
                    text = "Esta semana · Total $289K",
                    color = GrisStats,
                    fontSize = 9.sp
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                GraficoGastoDiario()
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            TarjetaStats(
                Modifier
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
                    text = "Agosto 2026 · Total $403.700",
                    color = GrisStats,
                    fontSize = 9.sp
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                CategoriaStats(
                    "Estudio",
                    "$ 165.000",
                    "41%",
                    0.41f,
                    "📚"
                )

                CategoriaStats(
                    "Compras",
                    "$ 89.000",
                    "22%",
                    0.22f,
                    "🛍"
                )

                CategoriaStats(
                    "Entretenimiento",
                    "$ 63.000",
                    "16%",
                    0.16f,
                    "🎮"
                )

                CategoriaStats(
                    "Comida",
                    "$ 62.000",
                    "15%",
                    0.15f,
                    "🍔"
                )

                CategoriaStats(
                    "Otros",
                    "$ 15.000",
                    "4%",
                    0.04f,
                    "📦"
                )

                CategoriaStats(
                    "Transporte",
                    "$ 9.700",
                    "2%",
                    0.02f,
                    "🚗"
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            TarjetaStats(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
            ) {

                Text(
                    text = "Comparativa mensual",
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

                GraficoComparativaMensual()

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "■  Mes actual",
                    color = GrisStats,
                    fontSize = 9.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

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
                        text = "💡 Insight del mes",
                        color = VerdeStats,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Text(
                        text = "Tu mayor gasto es Estudio con $165.000 (41% del total). Intenta reducirlo un 15% el próximo mes.",
                        color = Color(0xFFB5EBDD),
                        fontSize = 10.sp
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(FondoStats)
                .align(Alignment.TopCenter)
        )

        BarraInferiorStats(
            onInicio = onInicio,
            onGastos = onGastos,
            onPresupuesto = onPresupuesto,
            onStats = onStats,
            onPerfil = onPerfil
        )
    }
}

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

@Composable
private fun PeriodoStats(
    texto: String,
    seleccionado: Boolean,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .height(32.dp)
            .background(
                if (seleccionado) VerdeStats else AzulStats,
                RoundedCornerShape(9.dp)
            )
            .border(
                1.dp,
                if (seleccionado) VerdeStats else BordeStats,
                RoundedCornerShape(9.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = texto,
            color = if (seleccionado) {
                Color(0xFF00150F)
            } else {
                GrisStats
            },
            fontSize = 10.sp,
            fontWeight = if (seleccionado) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }
        )
    }
}

@Composable
private fun GraficoGastoDiario() {

    val valores = listOf(
        55,
        25,
        78,
        45,
        95,
        58,
        32
    )

    val dias = listOf(
        "L",
        "M",
        "X",
        "J",
        "V",
        "S",
        "D"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {

        valores.forEachIndexed { indice, valor ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .width(22.dp)
                        .height(75.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    Box(
                        modifier = Modifier
                            .width(22.dp)
                            .height(
                                (valor * 0.62f).dp
                            )
                            .background(
                                if (indice == 4) {
                                    AmarilloStats
                                } else if (
                                    indice == 2 ||
                                    indice == 5
                                ) {
                                    VerdeStats
                                } else {
                                    Color(0xFF14253A)
                                },
                                RoundedCornerShape(6.dp)
                            )
                    )
                }

                Text(
                    text = dias[indice],
                    color = GrisStats,
                    fontSize = 8.sp
                )
            }
        }
    }
}

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
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = simbolo,
                fontSize = 10.sp
            )

            Spacer(
                modifier = Modifier.width(7.dp)
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
                modifier = Modifier.width(4.dp)
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
                    .fillMaxWidth(progreso)
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

@Composable
private fun GraficoComparativaMensual() {

    val valores = listOf(
        55,
        78,
        70,
        62
    )

    val meses = listOf(
        "May",
        "Jun",
        "Jul",
        "Ago"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {

        valores.forEachIndexed { indice, valor ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .width(23.dp)
                        .height(70.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    Box(
                        modifier = Modifier
                            .width(23.dp)
                            .height(
                                (valor * 0.62f).dp
                            )
                            .background(
                                if (indice == 3) {
                                    VerdeStats
                                } else {
                                    Color(0xFF4A566B)
                                },
                                RoundedCornerShape(6.dp)
                            )
                    )
                }

                Text(
                    text = meses[indice],
                    color = GrisStats,
                    fontSize = 8.sp
                )
            }
        }
    }
}

@Composable
private fun androidx.compose.foundation.layout.BoxScope.BarraInferiorStats(
    onInicio: () -> Unit,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(
                Color(0xFF0A1423)
            )
            .border(
                1.dp,
                BordeStats
            )
            .align(Alignment.BottomCenter)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 7.dp,
                    vertical = 7.dp
                ),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            ItemBarraStats(
                "⌂",
                "Inicio",
                false,
                onInicio
            )

            ItemBarraStats(
                "+",
                "Gastos",
                false,
                onGastos
            )

            ItemBarraStats(
                "$",
                "Presupuesto",
                false,
                onPresupuesto
            )

            ItemBarraStats(
                "▥",
                "Stats",
                true,
                onStats
            )

            ItemBarraStats(
                "♙",
                "Perfil",
                false,
                onPerfil
            )
        }
    }
}

@Composable
private fun ItemBarraStats(
    simbolo: String,
    texto: String,
    activo: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .width(55.dp)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = simbolo,
            color = if (activo) VerdeStats else GrisStats,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = texto,
            color = if (activo) VerdeStats else GrisStats,
            fontSize = 8.sp
        )
    }
}