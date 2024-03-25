package com.example.android_ap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun NotificacionesLayout(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        NotificacionesTopBar(Modifier.fillMaxWidth())
        NotificacionesPanel(Modifier.fillMaxWidth().padding(vertical = 16.dp))
    }
}


@Composable
fun NotificacionesPanel(modifier: Modifier){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier){
        Row(){
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Todos")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "No leídas")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Mención")
            }
        }

        Text(text = "Lazy column con notificaciones aqui")
    }
}

//Carta base para las notificaciones
@Composable
fun NotificacionCard(nombre: String,
                     contenido: String,
                     modifier: Modifier){
    Card(){
        Column(modifier = modifier) {
            Text(text = nombre,
                style = TextStyle(
                    fontSize = 16.sp)
            )
            Card {
                Text(text = contenido,
                style = TextStyle(fontSize = 10.sp, color = Color.White),
                    modifier = Modifier
                        .background(color = Color(0xCC262626))
                        .padding(horizontal = 4.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun NotificacionesTopBar(modifier: Modifier) {
    Card(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "NOTIFICACIONES",
                style = TextStyle(fontSize = 24.sp),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotiPreview(){
    Android_APTheme {
//        NotificacionCard(
//            nombre = "Lucía Vega",
//            contenido = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
//            modifier = Modifier.padding(4.dp))
        NotificacionesLayout()
    }
}