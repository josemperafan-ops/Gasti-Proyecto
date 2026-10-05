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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import edu.unicauca.aplimovil.proyectogasti.data.local.GastiDatabase
import edu.unicauca.aplimovil.proyectogasti.ui.viewmodel.PerfilViewModel
import java.util.concurrent.TimeUnit

private val FondoPerfil = Color(0xFF050B14)
private val AzulPerfil = Color(0xFF101A2A)
private val AzulOscuroPerfil = Color(0xFF0C1524)
private val VerdePerfil = Color(0xFF00E5A8)
private val GrisPerfil = Color(0xFF8B9AAF)
private val BordePerfil = Color(0xFF26344A)

@Composable
fun PerfilScreen(
    onInicio: () -> Unit,
    onGastos: () -> Unit,
    onPresupuesto: () -> Unit,
    onStats: () -> Unit,
    onPerfil: () -> Unit,
    onIngresos: () -> Unit,
    onCerrarSesion: () -> Unit
) {

    val context = LocalContext.current.applicationContext

    val database = remember {
        GastiDatabase.getDatabase(context)
    }

    val usuario = FirebaseAuth
        .getInstance()
        .currentUser

    val correoUsuario = usuario?.email ?: ""

    val diasUsando =
        usuario?.metadata?.creationTimestamp?.let { fechaCreacion ->

            val diferencia =
                System.currentTimeMillis() - fechaCreacion

            TimeUnit.MILLISECONDS
                .toDays(diferencia)
                .coerceAtLeast(0)

        } ?: 0L

    // ---------------------------------------------------------
    // VIEWMODEL
    // ---------------------------------------------------------

    val viewModel: PerfilViewModel = viewModel(
        factory = PerfilViewModel.Factory(
            usuarioDao = database.usuarioDao(),
            gastoDao = database.gastoDao(),
            correoUsuario = correoUsuario,
            diasUsando = diasUsando
        )
    )

    // Estado del perfil
    val uiState by viewModel.uiState.collectAsState()

    // ---------------------------------------------------------
    // DATOS DEL USUARIO
    // ---------------------------------------------------------

    val nombreUsuario = usuario?.displayName
        ?.takeIf { it.isNotBlank() }
        ?: uiState.nombre
            .takeIf {
                it.isNotBlank() && it != "Usuario"
            }
        ?: "Usuario"

    val correoUsuarioMostrar = usuario?.email
        ?.takeIf { it.isNotBlank() }
        ?: uiState.correo

    val iniciales = nombreUsuario
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") {
            it.first().uppercase()
        }

    // ---------------------------------------------------------
    // CONTENEDOR PRINCIPAL
    // ---------------------------------------------------------

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPerfil)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    top = 30.dp,
                    bottom = 80.dp
                )
        ) {

            // ---------------------------------------------------------
            // ENCABEZADO DEL PERFIL
            // ---------------------------------------------------------

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AzulPerfil)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 20.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .background(
                            VerdePerfil,
                            RoundedCornerShape(17.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = iniciales.ifEmpty { "U" },
                        color = Color(0xFF00150F),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.width(14.dp)
                )

                Column {

                    Text(
                        text = nombreUsuario,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = correoUsuarioMostrar,
                        color = GrisPerfil,
                        fontSize = 10.sp
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Box(
                        modifier = Modifier
                            .background(
                                Color(0xFF092B2A),
                                RoundedCornerShape(20.dp)
                            )
                            .border(
                                1.dp,
                                VerdePerfil,
                                RoundedCornerShape(20.dp)
                            )
                            .padding(
                                horizontal = 9.dp,
                                vertical = 4.dp
                            )
                    ) {

                        Text(
                            text = "• Usuario de Gasti",
                            color = VerdePerfil,
                            fontSize = 8.sp
                        )
                    }
                }
            }

            // ---------------------------------------------------------
            // ESTADÍSTICAS
            // ---------------------------------------------------------

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                EstadisticaPerfil(
                    uiState.cantidadGastos.toString(),
                    "Gastos",
                    "registrados",
                    Modifier.weight(1f)
                )

                EstadisticaPerfil(
                    "$${String.format("%,.0f", uiState.totalGastado)}",
                    "Gastado",
                    "total",
                    Modifier.weight(1f)
                )

                EstadisticaPerfil(
                    uiState.diasUsando.toString(),
                    "Días",
                    "usando",
                    Modifier.weight(1f)
                )
            }

            // ---------------------------------------------------------
            // OPCIONES PRINCIPALES
            // ---------------------------------------------------------

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        AzulPerfil,
                        RoundedCornerShape(16.dp)
                    )
                    .border(
                        1.dp,
                        BordePerfil,
                        RoundedCornerShape(16.dp)
                    )
            ) {

                OpcionPerfil(
                    icono = "🎯",
                    titulo = "Metas de ahorro",
                    detalle = "3 activas",
                    ultimo = false,
                    onClick = {
                        // Próximamente
                    }
                )

                OpcionPerfil(
                    icono = "📋",
                    titulo = "Historial completo",
                    detalle = "${uiState.cantidadGastos} gastos",
                    ultimo = false,
                    onClick = {
                        // Próximamente
                    }
                )

                // -----------------------------------------------------
                // INGRESOS
                // -----------------------------------------------------

                OpcionPerfil(
                    icono = "💰",
                    titulo = "Ingresos",
                    detalle = "Registrar",
                    ultimo = false,
                    onClick = {
                        onIngresos()
                    }
                )

                OpcionPerfil(
                    icono = "🔔",
                    titulo = "Recordatorios",
                    detalle = "Activo",
                    ultimo = true,
                    onClick = {
                        // Próximamente
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(13.dp)
            )

            // ---------------------------------------------------------
            // CONFIGURACIÓN
            // ---------------------------------------------------------

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        AzulPerfil,
                        RoundedCornerShape(16.dp)
                    )
                    .border(
                        1.dp,
                        BordePerfil,
                        RoundedCornerShape(16.dp)
                    )
            ) {

                OpcionPerfil(
                    icono = "🌙",
                    titulo = "Modo oscuro",
                    detalle = "Activado",
                    ultimo = false,
                    onClick = {
                        // Próximamente
                    }
                )

                OpcionPerfil(
                    icono = "🌐",
                    titulo = "Moneda",
                    detalle = "COP $",
                    ultimo = false,
                    onClick = {
                        // Próximamente
                    }
                )

                OpcionPerfil(
                    icono = "📤",
                    titulo = "Exportar datos",
                    detalle = "CSV",
                    ultimo = false,
                    onClick = {
                        // Próximamente
                    }
                )

                OpcionPerfil(
                    icono = "⭐",
                    titulo = "Calificar Gasti",
                    detalle = "",
                    ultimo = true,
                    onClick = {
                        // Próximamente
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(13.dp)
            )

            // ---------------------------------------------------------
            // CERRAR SESIÓN
            // ---------------------------------------------------------

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(45.dp)
                    .background(
                        Color(0xFF26141C),
                        RoundedCornerShape(10.dp)
                    )
                    .border(
                        1.dp,
                        Color(0xFF713040),
                        RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        onCerrarSesion()
                    },
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Cerrar sesión",
                    color = Color(0xFFFF5C68),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Gasti v1.0.0 · Hecho con 💚",
                color = Color(0xFF526078),
                fontSize = 8.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        // -------------------------------------------------------------
        // BARRA INFERIOR
        // -------------------------------------------------------------

        BarraInferiorPerfil(
            modifier = Modifier.align(
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


// =====================================================================
// ESTADÍSTICA
// =====================================================================

@Composable
private fun EstadisticaPerfil(
    valor: String,
    titulo: String,
    detalle: String,
    modifier: Modifier
) {

    Box(
        modifier = modifier
            .height(69.dp)
            .background(
                AzulPerfil,
                RoundedCornerShape(15.dp)
            )
            .border(
                1.dp,
                BordePerfil,
                RoundedCornerShape(15.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = valor,
                color = if (titulo == "Ahorrado") {
                    VerdePerfil
                } else {
                    Color(0xFFFF7580)
                },
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = titulo,
                color = Color.White,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold
            )

            if (detalle.isNotEmpty()) {

                Text(
                    text = detalle,
                    color = GrisPerfil,
                    fontSize = 7.sp
                )
            }
        }
    }
}


// =====================================================================
// OPCIÓN DEL PERFIL
// =====================================================================

@Composable
private fun OpcionPerfil(
    icono: String,
    titulo: String,
    detalle: String,
    ultimo: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        AzulOscuroPerfil,
                        RoundedCornerShape(9.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = icono,
                    fontSize = 15.sp
                )
            }

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Text(
                text = titulo,
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = detalle,
                color = GrisPerfil,
                fontSize = 8.sp
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = "›",
                color = GrisPerfil,
                fontSize = 18.sp
            )
        }

        if (!ultimo) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BordePerfil)
            )
        }
    }
}


// =====================================================================
// BARRA INFERIOR
// =====================================================================

@Composable
private fun BarraInferiorPerfil(
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
            .background(AzulPerfil)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            ItemBarraPerfil(
                "⌂",
                "Inicio",
                false,
                onInicio
            )

            ItemBarraPerfil(
                "⊕",
                "Gastos",
                false,
                onGastos
            )

            ItemBarraPerfil(
                "$",
                "Presupuesto",
                false,
                onPresupuesto
            )

            ItemBarraPerfil(
                "│",
                "Stats",
                false,
                onStats
            )

            ItemBarraPerfil(
                "♙",
                "Perfil",
                true,
                onPerfil
            )
        }
    }
}


// =====================================================================
// ITEM DE LA BARRA INFERIOR
// =====================================================================

@Composable
private fun ItemBarraPerfil(
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
            color = if (activo) {
                VerdePerfil
            } else {
                GrisPerfil
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = texto,
            color = if (activo) {
                VerdePerfil
            } else {
                GrisPerfil
            },
            fontSize = 8.sp
        )
    }
}