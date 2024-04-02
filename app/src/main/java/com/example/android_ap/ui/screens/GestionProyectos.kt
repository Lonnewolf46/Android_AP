package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.android_ap.Colaborador
import com.example.android_ap.Estado
import com.example.android_ap.Proyecto
import com.example.android_ap.R
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun GestionProyectosLayout(
    listaProyectos: List<Proyecto>,
    listaColaboradores: List<Colaborador>,
    listaEstados: List<Estado>,
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
            modifier = Modifier.height(108.dp)
        )
        Text(text = "Proyectos",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
            )
        Column(Modifier.weight(1f)) {
            if (listaProyectos.isNotEmpty() &&
                listaColaboradores.isNotEmpty() &&
                listaEstados.isNotEmpty()
                ){
                LazyColumn {
                    items(listaProyectos) { proyecto ->

                        val estado: String = if (listaEstados.any { it.id == proyecto.idEstado })
                            listaEstados.firstOrNull { it.id == proyecto.idEstado }!!.estado
                        else "No fue posible recuperar el estado"

                        val responsable: String =
                            if (listaColaboradores.any { it.id == proyecto.idResponsable })
                                listaColaboradores.firstOrNull { it.id == proyecto.idResponsable }!!.nombre
                            else "No fue posible recuperar el responsable"


                        ProyectosCard(
                            nombreProyecto = proyecto.nombre,
                            estado = estado,
                            responsable = responsable,
                            onConsultar = { onConsultar() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            }
            else Card(Modifier.padding(8.dp)){ Text(text = "Error recuperando los proyectos", Modifier.padding(8.dp))}
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
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier){
                Column(modifier.weight(1f)) {
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