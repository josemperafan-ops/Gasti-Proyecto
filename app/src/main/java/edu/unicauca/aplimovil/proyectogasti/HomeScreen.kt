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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import edu.unicauca.aplimovil.proyectogasti.data.local.GastiDatabase
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.GastoEntity
import edu.unicauca.aplimovil.proyectogasti.data.repository.PresupuestoRepository
import edu.unicauca.aplimovil.proyectogasti.ui.viewmodel.HomeViewModel

private val Fondo = Color(0xFF050B14)
private val AzulTarjeta = Color(0xFF101A2A)
private val VerdeGasti = Color(0xFF00E5A8)
private val GrisTexto = Color(0xFF8B9AAF)
private val Borde = Color(0xFF26344A)

@Composable
fun HomeScreen(
    onRegistrarGasto: () -> Unit,
    onEditarGasto: (GastoEntity) -> Unit,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit
) {
    val context = LocalContext.current.applicationContext
    val database = remember { GastiDatabase.getDatabase(context) }
    val presupuestoRepository = remember { PresupuestoRepository(database.presupuestoDao()) }
    val currentUser = FirebaseAuth.getInstance().currentUser
    val correoUsuario = currentUser?.email ?: "correo@ejemplo.com"
    val nombreUsuario = currentUser?.displayName ?: currentUser?.email?.substringBefore("@") ?: "Usuario"
    val mesAnio = "Septiembre 2026"

    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.Factory(
            presupuestoRepository,
            database.gastoDao(),
            correoUsuario,
            mesAnio
        )
    )

    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val esCuentaNueva = uiState.listaGastos.isEmpty() && uiState.presupuestoTotal == 0.0

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
                        text = "Hola, $nombreUsuario",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = mesAnio,
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

            if (esCuentaNueva) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AzulTarjeta
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "👋 ¡Bienvenido a Gasti!",
                            color = VerdeGasti,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Para comenzar, configura tus presupuestos y registra tu primer gasto.",
                            color = GrisTexto,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = onPresupuesto,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = VerdeGasti),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "1. Configurar Presupuesto",
                                color = Color(0xFF00150F),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = onRegistrarGasto,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF182438)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "2. Registrar Primer Gasto",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            } else {
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
                            text = "Disponible en presupuesto",
                            color = GrisTexto,
                            fontSize = 11.sp
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "$ ${String.format("%,.0f", uiState.disponible)}",
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
                                    text = "Suma Presupuestos",
                                    color = GrisTexto,
                                    fontSize = 10.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(3.dp)
                                )

                                Text(
                                    text = "$ ${String.format("%,.0f", uiState.presupuestoTotal)}",
                                    color = VerdeGasti,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.End
                            ) {

                                Text(
                                    text = "Gastos Totales",
                                    color = GrisTexto,
                                    fontSize = 10.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(3.dp)
                                )

                                Text(
                                    text = "$ ${String.format("%,.0f", uiState.totalGastado)}",
                                    color = Color(0xFFFF666B),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
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
                    texto = "Presupuesto",
                    modifier = Modifier.weight(1f),
                    onClick = onPresupuesto
                )

                AccionRapida(
                    icono = "📊",
                    texto = "Estadísticas",
                    modifier = Modifier.weight(1f),
                    onClick = onStats
                )

                AccionRapida(
                    icono = "👤",
                    texto = "Perfil",
                    modifier = Modifier.weight(1f),
                    onClick = onPerfil
                )
            }

            if (!esCuentaNueva && uiState.listaGastos.isNotEmpty()) {
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
                        text = "Ver todos (${uiState.listaGastos.size})",
                        color = VerdeGasti,
                        fontSize = 10.sp
                    )
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                uiState.listaGastos.forEach { gasto ->
                    Spacer(modifier = Modifier.height(8.dp))
                    val icono = when (gasto.categoria) {
                        "Comida" -> "🍔"
                        "Transporte" -> "🚌"
                        "Estudio" -> "📚"
                        "Entretenimiento" -> "🎮"
                        "Compras" -> "🛍"
                        else -> "📦"
                    }
                    GastoReciente(
                        icono = icono,
                        nombre = gasto.descripcion,
                        detalle = "${gasto.categoria} · ${gasto.fecha}",
                        valor = "-$${String.format("%,.0f", gasto.monto)}",
                        onEditar = { onEditarGasto(gasto) },
                        onEliminar = { viewModel.eliminarGasto(gasto) }
                    )
                }
            }
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
            onGastos = onRegistrarGasto,
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
fun GastoReciente(
    icono: String,
    nombre: String,
    detalle: String,
    valor: String,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
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
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(Color(0xFF182438), CircleShape)
                    .clickable { onEditar() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✎",
                    color = Color.White,
                    fontSize = 11.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(Color(0xFF26344A), CircleShape)
                    .clickable { onEliminar() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✕",
                    color = Color(0xFFFF666B),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
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
