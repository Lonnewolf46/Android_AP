package com.example.android_ap.ui.popups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.android_ap.data.TareaCampos
import com.example.android_ap.ui.screens.Demo_ExposedDropdownMenuBox
import com.example.android_ap.ui.theme.Android_APTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaTarea(nombre: String,
               storyPoints: String,
               onValueChange: (TareaCampos, String) -> Unit,
               onConfirmar: () -> Unit,
               onCerrarClick: () -> Unit){
    AlertDialog(
        onDismissRequest = onCerrarClick,
        modifier = Modifier.clip(RoundedCornerShape(12.dp))
    ){
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(8.dp)
            ){
                Text("Tareas de Proyecto",
                    style = TextStyle(fontSize = 20.sp),
                    fontWeight = FontWeight.Bold
                )

                //Nombre tarea
                OutlinedTextField(
                    value = nombre,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    onValueChange = { onValueChange(TareaCampos.NOMBRE, it) },
                    label = { Text(text = "NOMBRE DE TARERA") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )

                //Story Points
                OutlinedTextField(
                    value = storyPoints,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    onValueChange = {
                        if (it.length <= 2) {
                        if (it.isDigitsOnly())
                            onValueChange(TareaCampos.STORYPOINTS, it) }
                    },
                    label = { Text(text = "STORY POINTS") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                Demo_ExposedDropdownMenuBox("ENCARGADO", Modifier.fillMaxWidth())

                Button(onClick = onConfirmar,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Crear tarea",
                        style = TextStyle(fontSize = 16.sp),
                        fontWeight = FontWeight.Bold
                        )

                }

                TextButton(onClick = onCerrarClick,
                    modifier = Modifier.align(Alignment.End)) {
                    Text(text = "Cerrar",
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Preview(showBackground=true)
@Composable
fun NuevaTareaPreview(){
    Android_APTheme {
    }
}