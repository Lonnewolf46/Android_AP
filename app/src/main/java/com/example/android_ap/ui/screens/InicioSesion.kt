package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.R
import com.example.android_ap.data.InicioSesionCampos
import com.example.android_ap.ui.theme.Android_APTheme


@Composable
fun InicioSesionLayout(nombre: String,
                       clave: String,
                       estado: Boolean,
                       onTextInput: (InicioSesionCampos, String) -> Unit,
                       onViewPassword: (Boolean) -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(33.dp)
            .fillMaxSize()
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.login_image),
            contentDescription = null
        )
        DatosInicioSesion(
            nombre,
            clave,
            estado,
            onTextInput,
            onViewPassword,
            Modifier.padding(14.dp),)

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(56.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Iniciar Sesión",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.padding(top=68.dp, bottom = 26.dp))

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)){
            Text(
                text = "¿No tienes una cuenta?",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 21.sp
                )
            )
            ClickableText(
                text = AnnotatedString(text = "Registrate"),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF0063E6),
                    textAlign = TextAlign.End
                )
            ) {
            }
        }
    }
}

@Composable
fun DatosInicioSesion(usuario: String,
                      password: String,
                      passwordVisible: Boolean,
                      onTextInput: (InicioSesionCampos, String)->Unit,
                      onViewPassword: (Boolean) -> Unit,
                      modifier: Modifier = Modifier){
    Column(verticalArrangement = Arrangement.spacedBy(19.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = modifier) {
        Text(
            text = "Inicio de sesión",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
        OutlinedTextField(
            value = usuario,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = {onTextInput(InicioSesionCampos.NOMBRE, it)},
            label = {Text(text="EMAIL")},
            placeholder = { Text("xxxxxx@institucional.cr") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = {onTextInput(InicioSesionCampos.CLAVE, it)},//onTextInput,
            label = {Text(text="CONTRASEÑA")},
            singleLine = true,
            placeholder = { Text("Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image:ImageVector = if (passwordVisible)
                    ImageVector.vectorResource(R.drawable.eye_open)
                else ImageVector.vectorResource(R.drawable.eye_closed)

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                val newValue = !passwordVisible

                IconButton(onClick = { onViewPassword(newValue)}) {
                    Icon(imageVector  = image,
                        description,
                        Modifier
                            .padding(1.dp)
                            .width(24.dp)
                            .height(24.dp))
                }
            }
        )

        ClickableText(
            text = AnnotatedString(text = "¿Olvidaste tu contraseña?"),
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF0063E6),
                textAlign = TextAlign.End
            ),
            modifier = Modifier.align(Alignment.End)
        ) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    Android_APTheme {

    }
}