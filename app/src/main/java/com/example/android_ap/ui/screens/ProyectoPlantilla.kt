package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.android_ap.data.ProyectoCampos
import com.example.android_ap.ui.popups.Warning
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun ProyectoPlantillaLayout(
    imagen: ImageVector,
    titulo: String,
    nombre: String,
    recursos: String,
    presupuesto: String,
    estado: String,
    descripcion: String,
    responsable: String,
    codigoResult: Int,
    onValueChange: (ProyectoCampos, String) -> Unit,
    onAsignarColaboradores: () -> Unit,
    onCrearTareas: () -> Unit,
    onCrearProyecto: () -> Unit,
    onCerrarPopUp: () -> Unit

){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize()
    ){
        Image(
            imageVector = imagen,
            contentDescription = null,
            modifier = Modifier.height(108.dp))
        Text(
            text = titulo,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        DatosProyecto(
            nombre = nombre,
            recursos = recursos,
            presupuesto = presupuesto,
            estado = estado,
            descripcion = descripcion,
            responsable = responsable,
            onValueChange = onValueChange,
            onAsignarColaboradores = onAsignarColaboradores,
            onCrearTareas = onCrearTareas,
            onCrearProyecto = onCrearProyecto,
            modifier = Modifier.fillMaxWidth())

        when(codigoResult){
            0 -> Warning(texto = "Proceso exitoso",
                onClose = { onCerrarPopUp() })

            1 -> Warning(texto = "Se requiere llenar todos los campos",
                onClose = { onCerrarPopUp() })
        }

    }

}

@Composable
fun DatosProyecto(
    nombre: String,
    recursos: String,
    presupuesto: String,
    estado: String,
    descripcion: String,
    responsable: String,
    onValueChange: (ProyectoCampos, String) -> Unit,
    onAsignarColaboradores: () -> Unit,
    onCrearTareas: () -> Unit,
    onCrearProyecto: () -> Unit,
    modifier: Modifier){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){

        //Nombre
        OutlinedTextField(
            value = nombre,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onValueChange(ProyectoCampos.NOMBRE, it) },
            label = { Text(text = "NOMBRE") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            modifier = modifier
        )

        //Recursos
        OutlinedTextField(
            value = recursos,
            singleLine = false,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onValueChange(ProyectoCampos.RECURSOS, it) },
            label = { Text(text = "RECURSOS") },
            modifier = modifier
        )

        //Presupuesto
        OutlinedTextField(
            value = presupuesto,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = {
                if (it.isDigitsOnly())
                    onValueChange(ProyectoCampos.PRESUPUESTO, it)
            },
            label = { Text(text = "PRESUPUESTO") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = modifier
        )

        Button(onClick = { onAsignarColaboradores() },
            modifier = modifier){
            Text(text = "Asignar colaboradores",
                fontSize = 20.sp,)
        }
        Button(onClick = { onCrearTareas() },
            modifier = modifier){
            Text(text = "Crear tareas",
                fontSize = 20.sp,)
        }

        //Estado del proyecto
        Demo_ExposedDropdownMenuBox("ESTADO DEL PROYECTO", modifier)

        //Descripcion
        OutlinedTextField(
            value = descripcion,
            singleLine = false,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onValueChange(ProyectoCampos.DESCRIPCION, it) },
            label = { Text(text = "DESCRIPCIÓN") },
            modifier = modifier
        )

        //Responsable del proyecto
        Demo_ExposedDropdownMenuBox("RESPONSABLE", modifier = modifier)

        Button(
            onClick = { onCrearProyecto() },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Crear proyecto",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProyecto() {
    Android_APTheme {
    }
}