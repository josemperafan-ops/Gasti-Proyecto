package edu.unicauca.aplimovil.proyectogasti.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

private val Fondo = Color(0xFF050B14)
private val AzulTarjeta = Color(0xFF101A2A)
private val VerdeGasti = Color(0xFF00E5A8)
private val GrisTexto = Color(0xFF8B9AAF)
private val Borde = Color(0xFF26344A)

@Composable
fun RegistroScreen(
    onRegistroExitoso: () -> Unit,
    onVolverLogin: () -> Unit
) {

    var nombre by remember {
        mutableStateOf("")
    }

    var correo by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
    }

    var confirmarContrasena by remember {
        mutableStateOf("")
    }

    var mensajeError by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .padding(horizontal = 28.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Crear cuenta",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Crea tu cuenta para comenzar a usar Gasti.",
            color = GrisTexto,
            fontSize = 12.sp
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    AzulTarjeta,
                    RoundedCornerShape(16.dp)
                )
                .padding(20.dp)
        ) {

            Text(
                text = "Nombre",
                color = GrisTexto,
                fontSize = 10.sp
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    mensajeError = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Tu nombre",
                        color = GrisTexto,
                        fontSize = 11.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdeGasti,
                    unfocusedBorderColor = Borde,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = VerdeGasti
                ),
                shape = RoundedCornerShape(9.dp)
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Text(
                text = "Correo electrónico",
                color = GrisTexto,
                fontSize = 10.sp
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            OutlinedTextField(
                value = correo,
                onValueChange = {
                    correo = it
                    mensajeError = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "correo@ejemplo.com",
                        color = GrisTexto,
                        fontSize = 11.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdeGasti,
                    unfocusedBorderColor = Borde,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = VerdeGasti
                ),
                shape = RoundedCornerShape(9.dp)
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Text(
                text = "Contraseña",
                color = GrisTexto,
                fontSize = 10.sp
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            OutlinedTextField(
                value = contrasena,
                onValueChange = {
                    contrasena = it
                    mensajeError = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        text = "Contraseña",
                        color = GrisTexto,
                        fontSize = 11.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdeGasti,
                    unfocusedBorderColor = Borde,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = VerdeGasti
                ),
                shape = RoundedCornerShape(9.dp)
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Text(
                text = "Confirmar contraseña",
                color = GrisTexto,
                fontSize = 10.sp
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            OutlinedTextField(
                value = confirmarContrasena,
                onValueChange = {
                    confirmarContrasena = it
                    mensajeError = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        text = "Repite tu contraseña",
                        color = GrisTexto,
                        fontSize = 11.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdeGasti,
                    unfocusedBorderColor = Borde,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = VerdeGasti
                ),
                shape = RoundedCornerShape(9.dp)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            if (mensajeError.isNotEmpty()) {

                Text(
                    text = mensajeError,
                    color = Color(0xFFFF6B6B),
                    fontSize = 11.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }

            Button(
                onClick = {

                    when {

                        nombre.isBlank() ||
                                correo.isBlank() ||
                                contrasena.isBlank() ||
                                confirmarContrasena.isBlank() -> {

                            mensajeError =
                                "Completa todos los campos."
                        }

                        contrasena != confirmarContrasena -> {

                            mensajeError =
                                "Las contraseñas no coinciden."
                        }

                        contrasena.length < 6 -> {

                            mensajeError =
                                "La contraseña debe tener mínimo 6 caracteres."
                        }

                        else -> {

                            FirebaseAuth
                                .getInstance()
                                .createUserWithEmailAndPassword(
                                    correo.trim(),
                                    contrasena
                                )
                                .addOnCompleteListener { tarea ->

                                    if (tarea.isSuccessful) {

                                        val usuario =
                                            FirebaseAuth
                                                .getInstance()
                                                .currentUser

                                        val perfil =
                                            UserProfileChangeRequest
                                                .Builder()
                                                .setDisplayName(nombre.trim())
                                                .build()

                                        usuario
                                            ?.updateProfile(perfil)
                                            ?.addOnCompleteListener {

                                                mensajeError = ""

                                                onRegistroExitoso()
                                            }

                                    } else {

                                        mensajeError =
                                            "No se pudo crear la cuenta. Verifica el correo."
                                    }
                                }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeGasti
                ),
                shape = RoundedCornerShape(9.dp)
            ) {

                Text(
                    text = "Crear cuenta",
                    color = Color(0xFF00150F),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = onVolverLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(9.dp)
            ) {

                Text(
                    text = "Ya tengo una cuenta",
                    color = VerdeGasti,
                    fontSize = 11.sp
                )
            }
        }
    }
}
