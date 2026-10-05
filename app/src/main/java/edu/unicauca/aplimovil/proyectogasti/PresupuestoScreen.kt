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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import edu.unicauca.aplimovil.proyectogasti.data.local.GastiDatabase
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.PresupuestoEntity
import edu.unicauca.aplimovil.proyectogasti.data.repository.PresupuestoRepository
import edu.unicauca.aplimovil.proyectogasti.ui.viewmodel.PresupuestoViewModel

private val Fondo = Color(0xFF050B14)
private val AzulTarjeta = Color(0xFF101A2A)
private val VerdeGasti = Color(0xFF00E5A8)
private val GrisTexto = Color(0xFF8B9AAF)
private val Borde = Color(0xFF26344A)
private val Amarillo = Color(0xFFFFD166)
private val FondoPresupuesto = Color(0xFF211D08)
private val BordePresupuesto = Color(0xFF6C5815)
private val FondoAdvertencia = Color(0xFF25231F)

/**
 * Pantalla de control de presupuestos en Jetpack Compose.
 * Visualiza la suma total del presupuesto mensual, el total gastado, el saldo restante
 * y la barra de progreso del consumo. Permite crear, modificar y eliminar presupuestos.
 */
@Composable
fun PresupuestoScreen(
    onBack: () -> Unit,
    onInicio: () -> Unit,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit
) {
    val context = LocalContext.current.applicationContext
    val database = remember { GastiDatabase.getDatabase(context) }
    val presupuestoRepository = remember { PresupuestoRepository(database.presupuestoDao()) }
    val correoUsuario = FirebaseAuth.getInstance().currentUser?.email ?: "correo@ejemplo.com"
    val mesAnio = "Septiembre 2026"

    val viewModel: PresupuestoViewModel = viewModel(
        factory = PresupuestoViewModel.Factory(
            presupuestoRepository,
            database.gastoDao(),
            correoUsuario,
            mesAnio
        )
    )

    val uiState by viewModel.uiState.collectAsState()

    var mostrarDialogo by remember { mutableStateOf(false) }
    var presupuestoEnEdicion by remember { mutableStateOf<PresupuestoEntity?>(null) }
    var nombreInput by remember { mutableStateOf("") }
    var montoInput by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

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
                    text = "Presupuestos",
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

            // Resumen general de presupuestos vs gastado
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
                                text = "SUMA TOTAL PRESUPUESTOS",
                                color = GrisTexto,
                                fontSize = 9.sp
                            )

                            Spacer(
                                modifier = Modifier.height(5.dp)
                            )

                            Text(
                                text = "$ ${String.format("%,.0f", uiState.presupuestoTotal)}",
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
                                .clickable {
                                    presupuestoEnEdicion = null
                                    nombreInput = ""
                                    montoInput = ""
                                    mostrarDialogo = true
                                }
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 8.dp
                                )
                        ) {

                            Text(
                                text = "+ Nuevo",
                                color = VerdeGasti,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
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
                                .fillMaxWidth(uiState.porcentajeUsado.coerceIn(0f, 1f))
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
                            text = "${(uiState.porcentajeUsado * 100).toInt()}% usado",
                            color = GrisTexto,
                            fontSize = 9.sp
                        )

                        Text(
                            text = "$ ${String.format("%,.0f", uiState.disponible)} restante",
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
                                text = "$ ${String.format("%,.0f", uiState.totalGastado)}",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column {

                            Text(
                                text = "Disponible",
                                color = GrisTexto,
                                fontSize = 8.sp
                            )

                            Text(
                                text = "$ ${String.format("%,.0f", uiState.disponible)}",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Tus Presupuestos Registrados",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            if (uiState.listaPresupuestos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AzulTarjeta, RoundedCornerShape(12.dp))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tienes presupuestos creados. Toca en '+ Nuevo' para agregar uno.",
                        color = GrisTexto,
                        fontSize = 11.sp
                    )
                }
            } else {
                uiState.listaPresupuestos.forEach { presupuesto ->
                    Spacer(modifier = Modifier.height(8.dp))
                    ItemPresupuestoCard(
                        presupuesto = presupuesto,
                        onEditar = {
                            presupuestoEnEdicion = presupuesto
                            nombreInput = presupuesto.nombre
                            montoInput = presupuesto.montoTotal.toInt().toString()
                            mostrarDialogo = true
                        },
                        onEliminar = {
                            viewModel.eliminarPresupuesto(presupuesto)
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // Suma de los presupuestos en la parte inferior
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
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Suma Total de Presupuestos",
                        color = GrisTexto,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$ ${String.format("%,.0f", uiState.presupuestoTotal)}",
                        color = VerdeGasti,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                title = { Text(if (presupuestoEnEdicion == null) "Nuevo Presupuesto" else "Editar Presupuesto") },
                text = {
                    Column {
                        Text("Nombre del presupuesto:")
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = nombreInput,
                            onValueChange = { nombreInput = it },
                            singleLine = true,
                            placeholder = { Text("Ej. Quincena, Comida...") }
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text("Monto total:")
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = montoInput,
                            onValueChange = { montoInput = it },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            placeholder = { Text("Ej. 300000") }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val monto = montoInput.toDoubleOrNull()
                            if (monto != null && monto > 0) {
                                viewModel.guardarPresupuesto(
                                    id = presupuestoEnEdicion?.id ?: 0L,
                                    nombre = nombreInput,
                                    monto = monto
                                )
                                mostrarDialogo = false
                                nombreInput = ""
                                montoInput = ""
                                presupuestoEnEdicion = null
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = VerdeGasti)
                    ) {
                        Text("Guardar", color = Color(0xFF00150F))
                    }
                },
                dismissButton = {
                    Button(onClick = { mostrarDialogo = false }) {
                        Text("Cancelar")
                    }
                }
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
fun ItemPresupuestoCard(
    presupuesto: PresupuestoEntity,
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
            .border(
                1.dp,
                Borde,
                RoundedCornerShape(12.dp)
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = presupuesto.nombre,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Mes: ${presupuesto.mesAnio}",
                color = GrisTexto,
                fontSize = 10.sp
            )
        }

        Text(
            text = "$ ${String.format("%,.0f", presupuesto.montoTotal)}",
            color = VerdeGasti,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 12.dp)
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
                .padding(horizontal = 8.dp),
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
            fontSize = 9.sp
        )
    }
}
