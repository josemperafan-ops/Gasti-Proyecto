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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val FondoGasto = Color(0xFF050B14)
private val AzulGasto = Color(0xFF101A2A)
private val VerdeGasto = Color(0xFF00E5A8)
private val GrisGasto = Color(0xFF8B9AAF)
private val BordeGasto = Color(0xFF26344A)

@Composable
fun RegistrarGastoScreen(
    onBack: () -> Unit,
    onInicio: () -> Unit,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit
) {

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoGasto)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(AzulGasto)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        FondoGasto,
                        RoundedCornerShape(8.dp)
                    )
                    .border(
                        1.dp,
                        BordeGasto,
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
                    text = "Registrar gasto",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Agrega un nuevo gasto",
                    color = GrisGasto,
                    fontSize = 10.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 70.dp,
                    bottom = 65.dp
                )
                .verticalScroll(scrollState)
                .padding(14.dp)
        ) {

            Text(
                text = "Monto del gasto",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        AzulGasto,
                        RoundedCornerShape(15.dp)
                    )
                    .border(
                        1.dp,
                        BordeGasto,
                        RoundedCornerShape(15.dp)
                    )
                    .padding(20.dp)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "$ 0",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        ValorRapido("5.000")
                        ValorRapido("10.000")
                        ValorRapido("20.000")
                        ValorRapido("50.000")
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Categoría",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                CategoriaGasto(
                    "🍔",
                    "Comida",
                    Modifier.weight(1f)
                )

                CategoriaGasto(
                    "🚌",
                    "Transporte",
                    Modifier.weight(1f)
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                CategoriaGasto(
                    "📚",
                    "Estudio",
                    Modifier.weight(1f)
                )

                CategoriaGasto(
                    "🎮",
                    "Entretenimiento",
                    Modifier.weight(1f)
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                CategoriaGasto(
                    "🛍",
                    "Compras",
                    Modifier.weight(1f)
                )

                CategoriaGasto(
                    "📦",
                    "Otros",
                    Modifier.weight(1f)
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Descripción",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(
                        AzulGasto,
                        RoundedCornerShape(10.dp)
                    )
                    .border(
                        1.dp,
                        BordeGasto,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(14.dp)
            ) {

                Text(
                    text = "Descripción opcional",
                    color = GrisGasto,
                    fontSize = 10.sp
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Fecha",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        AzulGasto,
                        RoundedCornerShape(10.dp)
                    )
                    .border(
                        1.dp,
                        BordeGasto,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(14.dp)
            ) {

                Text(
                    text = "12 de agosto de 2026",
                    color = GrisGasto,
                    fontSize = 10.sp
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Button(
                onClick = {
                    // PENDIENTE:
                    // Guardar gasto.
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeGasto
                ),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    text = "Guardar gasto",
                    color = Color(0xFF00150F),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        BarraInferiorGasto(
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
private fun ValorRapido(
    valor: String
) {

    Box(
        modifier = Modifier
            .background(
                Color(0xFF182438),
                RoundedCornerShape(8.dp)
            )
            .clickable {
                // PENDIENTE
            }
            .padding(
                horizontal = 8.dp,
                vertical = 6.dp
            )
    ) {

        Text(
            text = "$$valor",
            color = GrisGasto,
            fontSize = 8.sp
        )
    }
}

@Composable
private fun CategoriaGasto(
    icono: String,
    nombre: String,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .height(65.dp)
            .background(
                AzulGasto,
                RoundedCornerShape(11.dp)
            )
            .border(
                1.dp,
                BordeGasto,
                RoundedCornerShape(11.dp)
            )
            .clickable {
                // PENDIENTE
            },
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = icono,
                fontSize = 17.sp
            )

            Text(
                text = nombre,
                color = GrisGasto,
                fontSize = 8.sp
            )
        }
    }
}

@Composable
private fun BarraInferiorGasto(
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
            .background(AzulGasto)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            ItemBarraGasto(
                "⌂",
                "Inicio",
                false,
                onInicio
            )

            ItemBarraGasto(
                "⊕",
                "Gastos",
                true,
                onGastos
            )

            ItemBarraGasto(
                "$",
                "Presupuesto",
                false,
                onPresupuesto
            )

            ItemBarraGasto(
                "│",
                "Stats",
                false,
                onStats
            )

            ItemBarraGasto(
                "♙",
                "Perfil",
                false,
                onPerfil
            )
        }
    }
}

@Composable
private fun ItemBarraGasto(
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
            color = if (activo) VerdeGasto else GrisGasto,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = texto,
            color = if (activo) VerdeGasto else GrisGasto,
            fontSize = 8.sp
        )
    }
}