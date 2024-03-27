package com.example.android_ap.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import androidx.core.text.isDigitsOnly
import com.example.android_ap.R
import com.example.android_ap.data.RegistroCampos
import com.example.android_ap.ui.popups.Warning
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun RegistroLayout(
    nombre: String,
    cedula: String,
    telefono: String,
    email: String,
    clave: String,
    passwordVisible: Boolean,
    camposLlenos: Boolean,
    onTextInput: (RegistroCampos, String) -> Unit,
    onViewPassword: (Boolean) -> Unit,
    onInicioSesionTextClicked: () -> Unit,
    onRegistroClicked: () -> Unit,
    onDialogClose: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(33.dp)
            .fillMaxSize()
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.registro_image),
            contentDescription = null
        )
        DatosRegistro(
            nombre = nombre,
            cedula = cedula,
            telefono = telefono,
            email = email,
            clave = clave,
            passwordVisible = passwordVisible,
            onTextInput = onTextInput,
            onViewPassword = onViewPassword,
            modifier = Modifier.padding(8.dp)
        )

        Button(
            onClick = { onRegistroClicked() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(48.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Crear cuenta",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = "¿Ya tienes una cuenta?",
            modifier = Modifier.padding(4.dp)
        )

        ClickableText(
            text = AnnotatedString(text = "Inicia sesión"),
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF0044FF),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(top = 4.dp)
        ) { onInicioSesionTextClicked() }

    }

    if(!camposLlenos){
        Warning(
            texto = "Se requieren llenar todos los campos",
            onClose = { onDialogClose() })

    }

}

@Composable
fun DatosRegistro(
    nombre: String,
    cedula: String,
    telefono: String,
    email: String,
    clave: String,
    passwordVisible: Boolean,
    onTextInput: (RegistroCampos, String) -> Unit,
    onViewPassword: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(19.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(
            text = "Registro",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
        //Nombre
        OutlinedTextField(
            value = nombre,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onTextInput(RegistroCampos.NOMBRE, it) },
            label = { Text(text = "NOMBRE COMPLETO") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )
        //Cedula
        OutlinedTextField(
            value = cedula,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { if (it.length <= 11) {
                if (it.isDigitsOnly())
                    onTextInput(RegistroCampos.CEDULA, it) }
            },
            label = { Text(text = "CÉDULA") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        //Telefono
        OutlinedTextField(
            value = telefono,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { if (it.length <= 8) {
                if (it.isDigitsOnly())
                    onTextInput(RegistroCampos.TELEFONO, it) }
                            },
            label = { Text(text = "TELÉFONO") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        Demo_ExposedDropdownMenuBox("PROYECTO")

        Demo_ExposedDropdownMenuBox("DEPARTAMENTO")

        OutlinedTextField(
            value = email,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onTextInput(RegistroCampos.CORREO, it) },
            label = { Text(text = "EMAIL") },
            placeholder = { Text("xxxxxx@institucional.cr") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = clave,
            onValueChange = { onTextInput(RegistroCampos.CLAVE, it) },//onTextInput,
            label = { Text(text = "CONTRASEÑA") },
            singleLine = true,
            placeholder = { Text("Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image: ImageVector = if (passwordVisible)
                    ImageVector.vectorResource(R.drawable.eye_open)
                else ImageVector.vectorResource(R.drawable.eye_closed)

                // Please provide localized description for accessibility services
                val description =
                    if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                val newValue = !passwordVisible

                IconButton(onClick = { onViewPassword(newValue) }) {
                    Icon(
                        imageVector = image,
                        description,
                        Modifier
                            .padding(1.dp)
                            .width(24.dp)
                            .height(24.dp)
                    )
                }
            }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_ExposedDropdownMenuBox(titulo: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val listaProyectos =
        arrayOf("$titulo 1", "$titulo 2", "$titulo 3", "$titulo 4", "$titulo 5")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(listaProyectos[0]) }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedText,
                label = { Text(text = titulo) },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listaProyectos.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistro() {
    Android_APTheme {
        //RegistroLayout()
    }
}