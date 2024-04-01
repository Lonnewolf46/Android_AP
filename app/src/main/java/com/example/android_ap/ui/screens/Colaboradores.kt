package com.example.android_ap.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun ColaboradoresLayout(
    onAsignarClick: () -> Unit,
    onReasignarClick: () -> Unit,
    onEliminarClick: () -> Unit
){
    var showAsignarColaborador by rememberSaveable { mutableStateOf(false) }
    var showAsignarProyecto by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ){
        ColaboradoresContent(
            onAsignarClick = { showAsignarColaborador =! showAsignarColaborador },
            onReasignarClick = { showAsignarProyecto =! showAsignarProyecto },
            onEliminarClick = onEliminarClick
        )
    }
    if(showAsignarProyecto){
//        AgregarPlantillaLayout(
//            titulo = "Reasignación de proyecto",
//            onAsignarClick = { onReasignarClick() },
//            onCerrarClick = { showAsignarProyecto =! showAsignarProyecto }
//        )
    }
    else if(showAsignarColaborador){
//        AgregarPlantillaLayout(
//            titulo = "Nuevo colaborador",
//            onAsignarClick = { onAsignarClick() },
//            onCerrarClick = { showAsignarColaborador =! showAsignarColaborador }
//        )
    }
}

@Composable
private fun ColaboradoresContent(
    onAsignarClick: () -> Unit,
    onReasignarClick: () -> Unit,
    onEliminarClick: () -> Unit
){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Colaboradores",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Button(
            onClick = { onAsignarClick() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = "Asignar colaborador",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }

        /*TODO: LazyColumn con cartas*/
        ColaboradorCard(nombre = "Lucia Vega", onReasignarClick, onEliminarClick)

    }
}

@Composable
private fun ColaboradorCard(nombre: String,
                    onReasignarClick: () -> Unit,
                    onEliminarClick: () -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .padding(4.dp)) {
            Text(text = nombre,
                style = TextStyle(
                    fontSize = 16.sp),
                modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .weight(2f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onReasignarClick() },
                    //colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2962FF)),
                    modifier = Modifier
                ) {
                    Text(text = "Reasignar",
                        textAlign = TextAlign.Center)
                }
                Button(
                    onClick = { onEliminarClick() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF295C))
                ) {
                    Text(text = "Eliminar",
                        textAlign = TextAlign.Center,
                        color = Color(0xFFFFFFFF))
                }
            }
        }
    }
}

@Preview(showBackground=true)
@Composable
private fun Preview(){
    Android_APTheme {
        ColaboradoresLayout({},{},{})
    }
}