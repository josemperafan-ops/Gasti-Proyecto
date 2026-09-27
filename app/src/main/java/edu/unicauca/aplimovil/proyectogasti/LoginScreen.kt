package edu.unicauca.aplimovil.proyectogasti.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import edu.unicauca.aplimovil.proyectogasti.R

private val Fondo = Color(0xFF050B14)
private val AzulTarjeta = Color(0xFF101A2A)
private val VerdeGasti = Color(0xFF00E5A8)
private val GrisTexto = Color(0xFF8B9AAF)
private val Borde = Color(0xFF26344A)


@Composable
fun LoginScreen(
    onLogin: () -> Unit
) {

    var correo by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
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

            /*
             * =====================================================
             * LOGO DE GASTI
             *
             * Se utiliza el mismo logo:
             * res/drawable/logogasti
             * =====================================================
             */

            Image(
                painter = painterResource(
                    id = R.drawable.logogasti
                ),
                contentDescription = "Logo de Gasti",
                modifier = Modifier
                    .size(90.dp),
                contentScale = ContentScale.Fit
            )


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            /*
             * =====================================================
             * NOMBRE DE LA APLICACIÓN
             * =====================================================
             */

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


            /*
             * =====================================================
             * TARJETA DEL LOGIN
             * =====================================================
             */

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


                /*
                 * =================================================
                 * CORREO
                 * =================================================
                 */

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


                /*
                 * =================================================
                 * CONTRASEÑA
                 * =================================================
                 */

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


                /*
                 * =================================================
                 * OLVIDÉ MI CONTRASEÑA
                 * =================================================
                 */

                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = VerdeGasti,
                    fontSize = 10.sp,
                    modifier = Modifier.align(
                        Alignment.End
                    )
                )


                Spacer(
                    modifier = Modifier.height(18.dp)
                )


                /*
                 * =================================================
                 * BOTÓN ENTRAR
                 * =================================================
                 */

                Button(
                    onClick = {

                        // PENDIENTE:
                        // Más adelante validaremos el correo
                        // y la contraseña.

                        // Por ahora cualquier click lleva
                        // directamente al Home.

                        onLogin()
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


                /*
                 * =================================================
                 * BOTÓN GOOGLE
                 * =================================================
                 */

                OutlinedButton(
                    onClick = {

                        // PENDIENTE:
                        // Implementar inicio de sesión
                        // con Google.

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(9.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {

                    Text(
                        text = "Continuar con Google",
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}