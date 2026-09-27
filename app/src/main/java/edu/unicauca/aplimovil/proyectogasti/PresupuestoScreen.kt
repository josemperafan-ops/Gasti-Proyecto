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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Fondo = Color(0xFF050B14)
private val AzulTarjeta = Color(0xFF101A2A)
private val VerdeGasti = Color(0xFF00E5A8)
private val GrisTexto = Color(0xFF8B9AAF)
private val Borde = Color(0xFF26344A)
private val Amarillo = Color(0xFFFFD166)
private val FondoPresupuesto = Color(0xFF211D08)
private val BordePresupuesto = Color(0xFF6C5815)
private val FondoAdvertencia = Color(0xFF25231F)

@Composable
fun PresupuestoScreen(
    onBack: () -> Unit,
    onInicio: () -> Unit,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit
) {

    val scrollState = rememberScrollState()

    var presupuesto by remember {
        mutableStateOf("$500.000")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(89.dp)
                .background(AzulTarjeta)
                .padding(horizontal = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        Fondo,
                        RoundedCornerShape(8.dp)
                    )
                    .border(
                        1.dp,
                        Borde,
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        onBack()
                    },
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "‹",
                    color = Color.White,
                    fontSize = 22.sp
                )
            }

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Column {

                Text(
                    text = "Presupuesto",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Control mensual de gastos",
                    color = GrisTexto,
                    fontSize = 10.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 89.dp,
                    bottom = 65.dp
                )
                .verticalScroll(scrollState)
                .padding(horizontal = 11.dp)
        ) {

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        FondoPresupuesto,
                        RoundedCornerShape(17.dp)
                    )
                    .border(
                        1.dp,
                        BordePresupuesto,
                        RoundedCornerShape(17.dp)
                    )
                    .padding(17.dp)
            ) {

                Column {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {

                            Text(
                                text = "PRESUPUESTO AGOSTO",
                                color = GrisTexto,
                                fontSize = 9.sp
                            )

                            Spacer(
                                modifier = Modifier.height(5.dp)
                            )

                            Text(
                                text = presupuesto,
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(
                                    AzulTarjeta,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 8.dp
                                )
                        ) {

                            Text(
                                text = "Editar",
                                color = GrisTexto,
                                fontSize = 9.sp
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .background(
                                Color(0xFF182438),
                                RoundedCornerShape(10.dp)
                            )
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.81f)
                                .height(6.dp)
                                .background(
                                    Amarillo,
                                    RoundedCornerShape(10.dp)
                                )
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "81% usado",
                            color = GrisTexto,
                            fontSize = 9.sp
                        )

                        Text(
                            text = "$ 96.300 restante",
                            color = VerdeGasti,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(13.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {

                            Text(
                                text = "Gastado",
                                color = GrisTexto,
                                fontSize = 8.sp
                            )

                            Text(
                                text = "$ 403.700",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column {

                            Text(
                                text = "Por día restante",
                                color = GrisTexto,
                                fontSize = 8.sp
                            )

                            Text(
                                text = "$ 5.068",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        FondoAdvertencia,
                        RoundedCornerShape(11.dp)
                    )
                    .border(
                        1.dp,
                        Color(0xFF6C5C2B),
                        RoundedCornerShape(11.dp)
                    )
                    .padding(12.dp)
            ) {

                Column {

                    Text(
                        text = "⚠ ¡Cuidado!",
                        color = Amarillo,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Ya usaste el 81% de tu presupuesto. Revisa tus gastos.",
                        color = Amarillo,
                        fontSize = 9.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        AzulTarjeta,
                        RoundedCornerShape(15.dp)
                    )
                    .border(
                        1.dp,
                        Borde,
                        RoundedCornerShape(15.dp)
                    )
                    .padding(14.dp)
            ) {

                Column {

                    Text(
                        text = "Desglose por categoría",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    CategoriaPresupuesto(
                        "📚",
                        "Estudio",
                        "$ 165.000",
                        0.35f,
                        Color(0xFFAA7CFF)
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    CategoriaPresupuesto(
                        "🛍️",
                        "Compras",
                        "$ 89.000",
                        0.20f,
                        Amarillo
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    CategoriaPresupuesto(
                        "🎮",
                        "Entretenimiento",
                        "$ 63.000",
                        0.15f,
                        VerdeGasti
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    CategoriaPresupuesto(
                        "🍔",
                        "Comida",
                        "$ 62.000",
                        0.14f,
                        Color(0xFFFF8A3D)
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    CategoriaPresupuesto(
                        "📦",
                        "Otros",
                        "$ 15.000",
                        0.04f,
                        Color(0xFF9BA8BB)
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    CategoriaPresupuesto(
                        "🚌",
                        "Transporte",
                        "$ 9.700",
                        0.02f,
                        Color(0xFF4A9DFF)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Button(
                onClick = {
                    // PENDIENTE
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulTarjeta
                ),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    text = "Administrar presupuesto",
                    color = Color.White,
                    fontSize = 11.sp
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }

        BarraInferiorPresupuesto(
            modifier = Modifier.align(Alignment.BottomCenter),
            onInicio = onInicio,
            onGastos = onGastos,
            onPresupuesto = onPresupuesto,
            onStats = onStats,
            onPerfil = onPerfil
        )
    }
}

@Composable
fun CategoriaPresupuesto(
    icono: String,
    nombre: String,
    valor: String,
    progreso: Float,
    color: Color
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = icono,
                fontSize = 12.sp
            )

            Spacer(
                modifier = Modifier.width(7.dp)
            )

            Text(
                text = nombre,
                color = Color.White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = valor,
                color = Color.White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(
                    Color(0xFF182438),
                    RoundedCornerShape(10.dp)
                )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(progreso)
                    .height(4.dp)
                    .background(
                        color,
                        RoundedCornerShape(10.dp)
                    )
            )
        }
    }
}

@Composable
fun BarraInferiorPresupuesto(
    modifier: Modifier = Modifier,
    onInicio: () -> Unit,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(AzulTarjeta)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            ItemBarraPresupuesto(
                "⌂",
                "Inicio",
                false,
                onInicio
            )

            ItemBarraPresupuesto(
                "⊕",
                "Gastos",
                false,
                onGastos
            )

            ItemBarraPresupuesto(
                "$",
                "Presupuesto",
                true,
                onPresupuesto
            )

            ItemBarraPresupuesto(
                "│",
                "Stats",
                false,
                onStats
            )

            ItemBarraPresupuesto(
                "♙",
                "Perfil",
                false,
                onPerfil
            )
        }
    }
}

@Composable
fun ItemBarraPresupuesto(
    simbolo: String,
    texto: String,
    activo: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .width(58.dp)
            .height(65.dp)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = simbolo,
            color = if (activo) VerdeGasti else GrisTexto,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = texto,
            color = if (activo) VerdeGasti else GrisTexto,
            fontSize = 8.sp
        )
    }
}