package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.Notificacion
import com.example.android_ap.R
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun NotificacionesLayout(
    listaNotificaciones: List<Notificacion>
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        NotificacionesTopBar(Modifier.fillMaxWidth())
        Spacer(Modifier.padding(vertical = 16.dp))
        Column(modifier = Modifier.weight(1f)
        ){
            NotificacionesPanel(listaNotificaciones)
        }
    }
}


@Composable
private fun NotificacionesPanel(
    listaNotificaciones: List<Notificacion>
){
        if(listaNotificaciones.isNotEmpty())
        LazyColumn(Modifier.fillMaxWidth()) {
            items(listaNotificaciones){
                notificacion -> NotificacionCard(contenido = notificacion.mensaje,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth())
            }
        }
        else
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(imageVector = ImageVector.vectorResource(id = R.drawable.checkmark),
                    contentDescription = null, Modifier.padding(16.dp).height(80.dp))
                NotificacionCard(
                    contenido = "No hay notificaciones",
                    modifier = Modifier
                    .fillMaxWidth())
            }
}

//Carta base para las notificaciones
@Composable
private fun NotificacionCard(
                     contenido: String,
                     modifier: Modifier){
    Card(modifier.padding(4.dp)){
        Column(modifier = modifier) {
            Card {
                Text(text = contenido,
                style = TextStyle(fontSize = 14.sp, color = Color.White),
                    modifier = modifier
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp))
                        .background(color = Color(0xCC262626))
                        .padding(horizontal = 4.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun NotificacionesTopBar(modifier: Modifier) {
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
private fun NotiPreview(){
    Android_APTheme {
        //NotificacionesLayout(listOf(Notificacion(1, "Error obteniendo las notificaciones", 1, 1)))
    }
}