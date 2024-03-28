package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.R
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun ForoLayout(
    imagen: ImageVector,
    titulo: String,
    /*TODO: Contenidos del foro o algo*/
    onSendClick: () -> Unit
    ){
    var mensaje by rememberSaveable { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ){
        Image(imageVector = imagen, contentDescription = null,
            modifier = Modifier.weight(0.6f))
        Column(modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)){
            Text(text = titulo,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold)
            )
            
            /*TODO: LazyColumn*/

            //Tarjeta MOCKUP
            ForoCard(usuario = "Lucia Vega", texto = "Mensaje de prueba para al foro interno del proyecto de aplicación de cafetería y comprobar su funcionamiento.")

        }
        OutlinedTextField(
            value = mensaje,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { mensaje = it},
            label = { Text(text = "NUEVO MENSAJE") },
            trailingIcon = {
                IconButton(onClick = { onSendClick() }) {
                    Icon(imageVector = Icons.Filled.Send,
                        contentDescription = "Enviar") }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ForoCard(usuario: String, texto: String){
    Card(modifier = Modifier.fillMaxWidth()
    ){
        Column(verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)){
            Text(text = usuario,
                fontWeight = FontWeight.Bold)
            Text(text = texto)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview(){
    Android_APTheme {
        ForoLayout(
            imagen = ImageVector.vectorResource(id = R.drawable.forums),
            titulo = "Foro general",
            onSendClick = {})
    }
}