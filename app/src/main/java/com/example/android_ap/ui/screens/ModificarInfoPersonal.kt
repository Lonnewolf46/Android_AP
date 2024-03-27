package com.example.android_ap.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.android_ap.data.RegistroCampos
import com.example.android_ap.ui.popups.Warning
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun ModificarInfoPersonalLayout(
    telefono: String,
    email: String,
    camposLlenos: Boolean,
    onTextInput: (RegistroCampos, String) -> Unit,
    onActualizarClicked: () -> Unit,
    onDialogClose: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(33.dp)
            .fillMaxSize()
    ) {

        DatosPersonales(
            telefono = telefono,
            email = email,
            onTextInput = onTextInput,
            modifier = Modifier.padding(8.dp)
        )

        Button(
            onClick = { onActualizarClicked() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(48.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Modificar",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }

    }

    if(!camposLlenos){
        Warning(
            texto = "Se requieren llenar todos los campos",
            onClose = { onDialogClose() })

    }

}

@Composable
private fun DatosPersonales(
    telefono: String,
    email: String,
    onTextInput: (RegistroCampos, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(19.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(
            text = "Modifica tu información",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
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
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewModInfo() {
    Android_APTheme {
        //ModificarInfoPersonalLayout()
    }
}