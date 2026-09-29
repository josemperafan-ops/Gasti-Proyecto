package edu.unicauca.aplimovil.proyectogasti.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import edu.unicauca.aplimovil.proyectogasti.R

private val Fondo = Color(0xFF050B14)
private val AzulTarjeta = Color(0xFF101A2A)
private val VerdeGasti = Color(0xFF00E5A8)
private val GrisTexto = Color(0xFF8B9AAF)
private val Borde = Color(0xFF26344A)

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onRegistro: () -> Unit
) {

    var correo by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
    }

    var mensajeError by remember {
        mutableStateOf("")
    }

    var mostrarRecuperacion by remember {
        mutableStateOf(false)
    }

    var mensajeRecuperacion by remember {
        mutableStateOf("")
    }

    var mostrandoMensajeExito by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.logogasti
                ),
                contentDescription = "Logo de Gasti",
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Text(
                text = "Gasti",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Controla tus gastos.",
                color = GrisTexto,
                fontSize = 12.sp
            )

            Spacer(
                modifier = Modifier.height(30.dp)
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
                    text = "Iniciar sesión",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
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
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = VerdeGasti,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            mensajeRecuperacion = ""
                            mostrarRecuperacion = true
                        }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
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

                        if (correo.isBlank() || contrasena.isBlank()) {

                            mensajeError =
                                "Ingresa tu correo y contraseña."

                        } else {

                            FirebaseAuth
                                .getInstance()
                                .signInWithEmailAndPassword(
                                    correo.trim(),
                                    contrasena
                                )
                                .addOnCompleteListener { tarea ->

                                    if (tarea.isSuccessful) {

                                        mensajeError = ""

                                        onLogin()

                                    } else {

                                        mensajeError =
                                            "Correo o contraseña incorrectos."
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
                        text = "Entrar a Gasti",
                        color = Color(0xFF00150F),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                OutlinedButton(
                    onClick = onRegistro,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(9.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = VerdeGasti
                    )
                ) {

                    Text(
                        text = "Crear una cuenta",
                        fontSize = 11.sp
                    )
                }
            }
        }

        if (mostrarRecuperacion) {

            AlertDialog(
                onDismissRequest = {
                    mostrarRecuperacion = false
                },

                title = {
                    Text(
                        text = "Recuperar contraseña"
                    )
                },

                text = {

                    Column {

                        Text(
                            text = "Escribe el correo de tu cuenta y te enviaremos un enlace para cambiar la contraseña."
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        OutlinedTextField(
                            value = correo,
                            onValueChange = {
                                correo = it
                                mensajeRecuperacion = ""
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "correo@ejemplo.com"
                                )
                            }
                        )

                        if (mensajeRecuperacion.isNotEmpty()) {

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = mensajeRecuperacion,
                                color = Color(0xFFFF6B6B),
                                fontSize = 12.sp
                            )
                        }
                    }
                },

                confirmButton = {

                    TextButton(
                        onClick = {

                            if (correo.isBlank()) {

                                mensajeRecuperacion =
                                    "Ingresa tu correo."

                            } else {

                                FirebaseAuth
                                    .getInstance()
                                    .sendPasswordResetEmail(
                                        correo.trim()
                                    )
                                    .addOnCompleteListener { tarea ->

                                        if (tarea.isSuccessful) {

                                            mostrarRecuperacion = false
                                            mostrandoMensajeExito = true

                                        } else {

                                            mensajeRecuperacion =
                                                "No se pudo enviar el correo. Verifica el correo ingresado."
                                        }
                                    }
                            }
                        }
                    ) {

                        Text(
                            text = "Enviar",
                            color = VerdeGasti
                        )
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = {
                            mostrarRecuperacion = false
                        }
                    ) {

                        Text(
                            text = "Cancelar"
                        )
                    }
                }
            )
        }

        if (mostrandoMensajeExito) {

            AlertDialog(
                onDismissRequest = {
                    mostrandoMensajeExito = false
                },

                title = {
                    Text(
                        text = "Correo enviado"
                    )
                },

                text = {
                    Text(
                        text = "Revisa tu correo electrónico. Allí encontrarás un enlace para restablecer tu contraseña."
                    )
                },

                confirmButton = {

                    TextButton(
                        onClick = {
                            mostrandoMensajeExito = false
                        }
                    ) {

                        Text(
                            text = "Aceptar",
                            color = VerdeGasti
                        )
                    }
                }
            )
        }
    }
}
