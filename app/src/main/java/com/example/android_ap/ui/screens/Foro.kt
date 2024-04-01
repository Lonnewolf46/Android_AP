package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.ForumMessageClass
import com.example.android_ap.MensajeForoGeneral
import com.example.android_ap.MensajeForoProyecto
import com.example.android_ap.ui.popups.Warning
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun <T: ForumMessageClass>ForoLayout(
    imagen: ImageVector,
    titulo: String,
    texto: String,
    mensajes: List<T>,
    codigoResult: Int,
    onValueChange: (String) -> Unit,
    cerrarEmergente: () -> Unit,
    onSendClick: () -> Unit
    ){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ){
        Image(imageVector = imagen, contentDescription = null,
            modifier = Modifier.height(108.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Text(text = titulo,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold)
            )
        }

        Column(Modifier
                .weight(1f)
                .fillMaxWidth()
        ){
            if (mensajes.isNotEmpty()) {
                LazyColumn {
                    items(mensajes) { mensaje ->
                        when(mensaje) {
                            is MensajeForoGeneral ->
                                ForoCard(
                                    texto = mensaje.mensaje,
                                    fecha = "Fecha: ${mensaje.fecha.substring(0, 10)}"
                                )
                            is MensajeForoProyecto ->
                                ForoCard(
                                    texto = mensaje.mensaje,
                                    fecha = "Fecha: ${mensaje.fecha.substring(0, 10)}"
                                )
                        }
                    }
                }
            }
            else{
                ForoCard(texto = "No hay mensajes", fecha = "")
            }
        }
        OutlinedTextField(
            value = texto,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onValueChange(it) },
            label = { Text(text = "NUEVO MENSAJE") },
            trailingIcon = {
                IconButton(onClick = { onSendClick() }) {
                    Icon(imageVector = Icons.Filled.Send,
                        contentDescription = "Enviar") }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

    when(codigoResult){
        -2 ->  Warning(
            texto = "Se ha producido un error inesperado. Por favor inténtelo de nuevo.",
            onClose = cerrarEmergente )

        0 -> Warning(
            texto = "Mensaje publicado",
            onClose = cerrarEmergente )

        1 -> Warning(
            texto = "No se puede publicar un mensaje vacío",
            onClose = cerrarEmergente )

        3 ->  Warning(
            texto = "Se produjo un error de red. Verifique su conexión a internet",
            onClose = cerrarEmergente )
    }

}

@Composable
private fun ForoCard(texto: String, fecha: String){
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)
    ){
        Column(verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)){
            Text(text = texto)
            Text(text = fecha)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview(){
    Android_APTheme {

    }
}