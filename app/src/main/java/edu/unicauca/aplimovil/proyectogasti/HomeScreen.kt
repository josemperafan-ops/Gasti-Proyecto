package edu.unicauca.aplimovil.proyectogasti.ui.theme

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Fondo = Color(0xFF050B14)
private val AzulTarjeta = Color(0xFF101A2A)
private val VerdeGasti = Color(0xFF00E5A8)
private val GrisTexto = Color(0xFF8B9AAF)
private val Borde = Color(0xFF26344A)

@Composable
fun HomeScreen(
    onRegistrarGasto: () -> Unit,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit
) {

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 20.dp,
                    bottom = 80.dp
                )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "Hola, Jhoan, Jose y Stiven",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Agosto 2026",
                        color = GrisTexto,
                        fontSize = 12.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(
                            AzulTarjeta,
                            CircleShape
                        )
                        .border(
                            1.dp,
                            Borde,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "•",
                        color = VerdeGasti,
                        fontSize = 22.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AzulTarjeta
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Balance disponible",
                        color = GrisTexto,
                        fontSize = 11.sp
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Text(
                        text = "$ 226.300",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {

                            Text(
                                text = "Ingresos",
                                color = GrisTexto,
                                fontSize = 10.sp
                            )

                            Spacer(
                                modifier = Modifier.height(3.dp)
                            )

                            Text(
                                text = "$ 630.000",
                                color = VerdeGasti,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {

                            Text(
                                text = "Gastos",
                                color = GrisTexto,
                                fontSize = 10.sp
                            )

                            Spacer(
                                modifier = Modifier.height(3.dp)
                            )

                            Text(
                                text = "$ 403.700",
                                color = Color(0xFFFF666B),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Acciones rápidas",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                AccionRapida(
                    icono = "💸",
                    texto = "Registrar\ngasto",
                    modifier = Modifier.weight(1f),
                    onClick = onRegistrarGasto
                )

                AccionRapida(
                    icono = "💰",
                    texto = "Agregar\ningreso",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        // PENDIENTE
                    }
                )

                AccionRapida(
                    icono = "🎯",
                    texto = "Ver\nmetas",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        // PENDIENTE
                    }
                )

                AccionRapida(
                    icono = "📋",
                    texto = "Historial",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        // PENDIENTE
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "Gastos por categoría",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AzulTarjeta
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier.size(130.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            Canvas(
                                modifier = Modifier.size(115.dp)
                            ) {

                                val strokeWidth = 28f

                                drawArc(
                                    color = Color(0xFFFF8A00),
                                    startAngle = -90f,
                                    sweepAngle = 110f,
                                    useCenter = false,
                                    style = Stroke(strokeWidth)
                                )

                                drawArc(
                                    color = Color(0xFF3E8BFF),
                                    startAngle = 20f,
                                    sweepAngle = 80f,
                                    useCenter = false,
                                    style = Stroke(strokeWidth)
                                )

                                drawArc(
                                    color = Color(0xFF9B6CFF),
                                    startAngle = 100f,
                                    sweepAngle = 65f,
                                    useCenter = false,
                                    style = Stroke(strokeWidth)
                                )

                                drawArc(
                                    color = Color(0xFF00E5A8),
                                    startAngle = 165f,
                                    sweepAngle = 55f,
                                    useCenter = false,
                                    style = Stroke(strokeWidth)
                                )

                                drawArc(
                                    color = Color(0xFFFF666B),
                                    startAngle = 220f,
                                    sweepAngle = 45f,
                                    useCenter = false,
                                    style = Stroke(strokeWidth)
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = "$403K",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "Total",
                                    color = GrisTexto,
                                    fontSize = 9.sp
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier.width(14.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            CategoriaResumen(
                                icono = "🍔",
                                nombre = "Comida",
                                porcentaje = "35%"
                            )

                            CategoriaResumen(
                                icono = "🚌",
                                nombre = "Transporte",
                                porcentaje = "25%"
                            )

                            CategoriaResumen(
                                icono = "📚",
                                nombre = "Estudio",
                                porcentaje = "18%"
                            )

                            CategoriaResumen(
                                icono = "🎮",
                                nombre = "Entretenimiento",
                                porcentaje = "12%"
                            )

                            CategoriaResumen(
                                icono = "📦",
                                nombre = "Otros",
                                porcentaje = "10%"
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Gastos recientes",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Ver todos",
                    color = VerdeGasti,
                    fontSize = 10.sp
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            GastoReciente(
                icono = "🍔",
                nombre = "Almuerzo restaurante",
                detalle = "Comida · 12 ago",
                valor = "-$19K"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            GastoReciente(
                icono = "🚌",
                nombre = "Bus universidad",
                detalle = "Transporte · 12 ago",
                valor = "-$3K"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            GastoReciente(
                icono = "📚",
                nombre = "Fotocopias y materiales",
                detalle = "Estudio · 11 ago",
                valor = "-$45K"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            GastoReciente(
                icono = "🎮",
                nombre = "Cine con amigos",
                detalle = "Entretenimiento · 10 ago",
                valor = "-$30K"
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 18.dp,
                    bottom = 78.dp
                )
                .size(58.dp)
                .background(
                    VerdeGasti,
                    CircleShape
                )
                .clickable {
                    onRegistrarGasto()
                },
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "+",
                color = Color(0xFF00150F),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onGastos = onGastos,
            onPresupuesto = onPresupuesto,
            onStats = onStats,
            onPerfil = onPerfil
        )
    }
}

@Composable
fun AccionRapida(
    icono: String,
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .height(80.dp)
            .background(
                AzulTarjeta,
                RoundedCornerShape(12.dp)
            )
            .border(
                1.dp,
                Borde,
                RoundedCornerShape(12.dp)
            )
            .clickable {
                onClick()
            }
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = icono,
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = texto,
            color = GrisTexto,
            fontSize = 8.sp
        )
    }
}

@Composable
fun CategoriaResumen(
    icono: String,
    nombre: String,
    porcentaje: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = icono,
            fontSize = 13.sp
        )

        Spacer(
            modifier = Modifier.width(6.dp)
        )

        Text(
            text = nombre,
            color = GrisTexto,
            fontSize = 9.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = porcentaje,
            color = Color.White,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun GastoReciente(
    icono: String,
    nombre: String,
    detalle: String,
    valor: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                AzulTarjeta,
                RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 10.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(38.dp)
                .background(
                    Color(0xFF182438),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = icono,
                fontSize = 16.sp
            )
        }

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = nombre,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = detalle,
                color = GrisTexto,
                fontSize = 9.sp
            )
        }

        Text(
            text = valor,
            color = Color(0xFFFF666B),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(Color(0xFF101A2A))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            ItemBarraInferior(
                simbolo = "⌂",
                texto = "Inicio",
                activo = true,
                onClick = {}
            )

            ItemBarraInferior(
                simbolo = "⊕",
                texto = "Gastos",
                activo = false,
                onClick = onGastos
            )

            ItemBarraInferior(
                simbolo = "$",
                texto = "Presupuesto",
                activo = false,
                onClick = onPresupuesto
            )

            ItemBarraInferior(
                simbolo = "│",
                texto = "Stats",
                activo = false,
                onClick = onStats
            )

            ItemBarraInferior(
                simbolo = "♙",
                texto = "Perfil",
                activo = false,
                onClick = onPerfil
            )
        }
    }
}

@Composable
fun ItemBarraInferior(
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

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Text(
            text = texto,
            color = if (activo) VerdeGasti else GrisTexto,
            fontSize = 8.sp
        )
    }
}