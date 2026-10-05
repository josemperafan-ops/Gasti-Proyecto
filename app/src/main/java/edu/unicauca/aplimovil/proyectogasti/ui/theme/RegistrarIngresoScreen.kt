package edu.unicauca.aplimovil.proyectogasti.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import edu.unicauca.aplimovil.proyectogasti.data.local.GastiDatabase
import edu.unicauca.aplimovil.proyectogasti.data.repository.IngresoRepository
import edu.unicauca.aplimovil.proyectogasti.ui.viewmodel.IngresoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RegistrarIngresoScreen(
    onVolver: () -> Unit
) {

    val context = androidx.compose.ui.platform.LocalContext.current

    val database = remember {
        GastiDatabase.getDatabase(
            context.applicationContext
        )
    }

    val correoUsuario =
        FirebaseAuth.getInstance()
            .currentUser
            ?.email
            ?: ""

    val repository = remember {
        IngresoRepository(
            database.ingresoDao()
        )
    }

    val viewModel: IngresoViewModel = viewModel(
        factory = IngresoViewModel.Factory(
            repository,
            correoUsuario
        )
    )

    var monto by remember {
        mutableStateOf("")
    }

    var descripcion by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Registrar ingreso"
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        OutlinedTextField(
            value = monto,
            onValueChange = {
                monto = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Monto")
            }
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = {
                descripcion = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Descripción")
            }
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Button(
            onClick = {

                val montoDouble =
                    monto.toDoubleOrNull()

                if (montoDouble != null) {

                    val fecha =
                        SimpleDateFormat(
                            "dd 'de' MMMM 'de' yyyy",
                            Locale("es", "ES")
                        ).format(Date())

                    viewModel.registrarIngreso(
                        monto = montoDouble,
                        descripcion = descripcion,
                        fecha = fecha
                    )

                    onVolver()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Guardar ingreso")
        }
    }
}