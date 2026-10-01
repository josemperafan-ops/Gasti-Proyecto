package edu.unicauca.aplimovil.proyectogasti.ui.theme

import android.app.DatePickerDialog
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.GastoEntity
import edu.unicauca.aplimovil.proyectogasti.data.repository.GastoRepository
import edu.unicauca.aplimovil.proyectogasti.ui.viewmodel.RegistrarGastoViewModel
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

private val FondoGasto = Color(0xFF050B14)
private val AzulGasto = Color(0xFF101A2A)
private val VerdeGasto = Color(0xFF00E5A8)
private val GrisGasto = Color(0xFF8B9AAF)
private val BordeGasto = Color(0xFF26344A)

private fun obtenerFechaActualFormateada(): String {
    val hoy = LocalDate.now()
    val dia = hoy.dayOfMonth
    val mes = hoy.month.getDisplayName(java.time.format.TextStyle.FULL, Locale.forLanguageTag("es"))
    val anio = hoy.year
    return "$dia de $mes de $anio"
}

@Composable
fun RegistrarGastoScreen(
    gastoAEditar: GastoEntity? = null,
    onBack: () -> Unit,
    onInicio: () -> Unit,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit
) {
    val context = LocalContext.current
    val appContext = context.applicationContext
    val database = remember { GastiDatabase.getDatabase(appContext) }
    val repository = remember { GastoRepository(database.gastoDao()) }
    val correoUsuario = FirebaseAuth.getInstance().currentUser?.email ?: "correo@ejemplo.com"
    val mesAnio = "Septiembre 2026"

    val viewModel: RegistrarGastoViewModel = viewModel(
        factory = RegistrarGastoViewModel.Factory(
            repository,
            database.presupuestoDao(),
            database.gastoDao(),
            correoUsuario,
            mesAnio
        )
    )

    val mensajeError by viewModel.mensajeError.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navegarInicio.collectLatest {
            onInicio()
        }
    }

    val scrollState = rememberScrollState()

    var montoInput by remember {
        mutableStateOf(if (gastoAEditar != null && gastoAEditar.monto > 0) gastoAEditar.monto.toInt().toString() else "")
    }
    var categoriaSeleccionada by remember {
        mutableStateOf(gastoAEditar?.categoria ?: "Comida")
    }
    var descripcionInput by remember {
        mutableStateOf(gastoAEditar?.descripcion ?: "")
    }
    var fechaInput by remember {
        mutableStateOf(gastoAEditar?.fecha ?: obtenerFechaActualFormateada())
    }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val mesesNombres = arrayOf(
                "enero", "febrero", "marzo", "abril", "mayo", "junio",
                "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
            )
            val nombreMes = mesesNombres[month]
            fechaInput = "$dayOfMonth de $nombreMes de $year"
            viewModel.limpiarError()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.maxDate = System.currentTimeMillis()
    }

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
                    text = if (gastoAEditar == null) "Registrar gasto" else "Editar gasto",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = if (gastoAEditar == null) "Agrega un nuevo gasto" else "Modifica los datos del gasto",
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

                    OutlinedTextField(
                        value = montoInput,
                        onValueChange = {
                            montoInput = it
                            viewModel.limpiarError()
                        },
                        placeholder = {
                            Text(text = "$ 0", color = GrisGasto, fontSize = 24.sp)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = VerdeGasto,
                            unfocusedBorderColor = BordeGasto
                        )
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        ValorRapido("5.000") { montoInput = "5000"; viewModel.limpiarError() }
                        ValorRapido("10.000") { montoInput = "10000"; viewModel.limpiarError() }
                        ValorRapido("20.000") { montoInput = "20000"; viewModel.limpiarError() }
                        ValorRapido("50.000") { montoInput = "50000"; viewModel.limpiarError() }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Categoría: $categoriaSeleccionada",
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
                    seleccionada = categoriaSeleccionada == "Comida",
                    modifier = Modifier.weight(1f)
                ) { categoriaSeleccionada = "Comida"; viewModel.limpiarError() }

                CategoriaGasto(
                    "🚌",
                    "Transporte",
                    seleccionada = categoriaSeleccionada == "Transporte",
                    modifier = Modifier.weight(1f)
                ) { categoriaSeleccionada = "Transporte"; viewModel.limpiarError() }
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
                    seleccionada = categoriaSeleccionada == "Estudio",
                    modifier = Modifier.weight(1f)
                ) { categoriaSeleccionada = "Estudio"; viewModel.limpiarError() }

                CategoriaGasto(
                    "🎮",
                    "Entretenimiento",
                    seleccionada = categoriaSeleccionada == "Entretenimiento",
                    modifier = Modifier.weight(1f)
                ) { categoriaSeleccionada = "Entretenimiento"; viewModel.limpiarError() }
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
                    seleccionada = categoriaSeleccionada == "Compras",
                    modifier = Modifier.weight(1f)
                ) { categoriaSeleccionada = "Compras"; viewModel.limpiarError() }

                CategoriaGasto(
                    "📦",
                    "Otros",
                    seleccionada = categoriaSeleccionada == "Otros",
                    modifier = Modifier.weight(1f)
                ) { categoriaSeleccionada = "Otros"; viewModel.limpiarError() }
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

            OutlinedTextField(
                value = descripcionInput,
                onValueChange = {
                    descripcionInput = it
                    viewModel.limpiarError()
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Ej. Almuerzo ejecutivo",
                        color = GrisGasto,
                        fontSize = 10.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = VerdeGasto,
                    unfocusedBorderColor = BordeGasto
                )
            )

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
                    .clickable {
                        datePickerDialog.show()
                    }
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = fechaInput,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            if (mensajeError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = mensajeError,
                    color = Color(0xFFFF6B6B),
                    fontSize = 12.sp
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Button(
                onClick = {
                    viewModel.guardarGasto(
                        id = gastoAEditar?.id ?: 0L,
                        montoStr = montoInput,
                        categoria = categoriaSeleccionada,
                        descripcion = descripcionInput,
                        fecha = fechaInput
                    )
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
                    text = if (gastoAEditar == null) "Guardar gasto" else "Actualizar gasto",
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
    valor: String,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .background(
                Color(0xFF182438),
                RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
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
    seleccionada: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .height(65.dp)
            .background(
                if (seleccionada) Color(0xFF1E3A5F) else AzulGasto,
                RoundedCornerShape(11.dp)
            )
            .border(
                1.dp,
                if (seleccionada) VerdeGasto else BordeGasto,
                RoundedCornerShape(11.dp)
            )
            .clickable { onClick() },
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
                color = if (seleccionada) VerdeGasto else GrisGasto,
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
