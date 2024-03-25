package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun TrabajoLayout(){
    Column(horizontalAlignment = Alignment.CenterHorizontally
        ,modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        ProyectoActualTopBar(proyecto = "Proyecto 1")
        Tareas(Modifier.padding(16.dp))

    }
}

@Composable
fun Tareas(modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f)) {
                Text(text = "MIS TAREAS",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center)
                )
            }
            Spacer(Modifier.padding(4.dp))
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f)) {
                Text(text = "TAREAS DEL PROYECTO",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center)
                )
            }
        }
        Divider(thickness = 2.dp,
            modifier = Modifier.padding(vertical = 16.dp))
        Text("Tareas y LazyColumn aqui")

        Button(onClick = { /*TODO*/ }) {
            Image(Icons.Filled.Add, contentDescription = null)
        }
    }
}


@Composable
fun ProyectoActualTopBar(proyecto: String) {
    Card() {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 4.dp)) {
            Text(
                text = proyecto,
                style = TextStyle(fontSize = 24.sp),
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.weight(3f))
            Button(onClick = { /*TODO*/ }) {
                Image(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}



@Preview(showBackground=true)
@Composable
fun PreviewTrabajo(){
    Android_APTheme {
        TrabajoLayout()
    }
}