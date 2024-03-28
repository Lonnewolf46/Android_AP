package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.R
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun GestionProyectosLayout(
    /*TODO: Array con todos los proyectos o una forma de obtenerlos*/
    onConsultar: () -> Unit
    ){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.manage_proj), contentDescription = null,
            modifier = Modifier.weight(0.6f)
        )

        Column(
            modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Proyectos",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold)
            )

            /*TODO: Colocar Lazy Column*/

            //MOCKUP DATA
            ProyectosCard(
                nombreProyecto = "Aplicación de cafeteria",
                estado = "En progreso",
                responsable = "Lucía Vega",
                onConsultar = { onConsultar() },
                modifier = Modifier.fillMaxWidth()
            )
        }

    }

}

@Composable
private fun ProyectosCard(
    nombreProyecto: String,
    estado: String,
    responsable: String,
    onConsultar: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(modifier = modifier){
        Column(verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(8.dp)) {
            Text(
                text = nombreProyecto,
                style = TextStyle(
                    fontSize = 16.sp)
            )
            Text(text = estado)
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Responsable: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = responsable)
                }
                Button(onClick = { onConsultar() }) {
                    Text(text = "Consultar")
                }
            }
        }

    }
}

@Preview(showBackground=true)
@Composable
private fun Preview(){
    Android_APTheme {
    }
}