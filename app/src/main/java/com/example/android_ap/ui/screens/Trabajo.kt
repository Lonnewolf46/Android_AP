package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import com.example.android_ap.data.TareaCampos
import com.example.android_ap.ui.popups.NuevaTarea
import com.example.android_ap.ui.theme.Android_APTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.outlined.Create
import com.example.android_ap.ui.popups.Warning

/**
 * Componente principal de la interfaz de Trabajo
*/
@Composable
fun TrabajoLayout(
    nombreProyecto: String,
    nombreTarea: String,
    storyPoints: String,
    encargado: String,
    crearTareaVisible: Boolean,
    codigoResult: Int,
    onEditarTareaClick: () -> Unit,
    onOpcionesProyectoClick: () -> Unit,
    onTareaValueChange: (TareaCampos, String) -> Unit,
    onTareaEncargadoSelectionChange: (String) -> Unit,
    onTareaConfirmar: () -> Unit,
    onTareaCerrarClick: () -> Unit
){
    Column(horizontalAlignment = Alignment.CenterHorizontally
        ,modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        ProyectoActualTopBar(proyecto = nombreProyecto, onOpcionesProyectoClick)
        Tareas(Modifier.padding(16.dp))

        //Si se ha dado click a crear tarea
        if (crearTareaVisible) {
                NuevaTarea(
                    nombre = nombreTarea,
                    storyPoints = storyPoints,
                    encargado = encargado,
                    codigoResult = codigoResult,
                    onValueChange = onTareaValueChange,
                    onEncargadoSelectionChange = onTareaEncargadoSelectionChange,
                    onConfirmar = onTareaConfirmar,
                    onCerrarClick = onTareaCerrarClick
                )
        }
        //Si la tarea fue creada satisfactoriamente
        else if(codigoResult == 0){
            Warning(
                texto = "Tarea creada",
                onClose = { onTareaCerrarClick() })
        }
    }
}

/**
 * Componente de texto en la parte superior
 */
@Composable
private fun ProyectoActualTopBar(
    proyecto: String,
    onMasClick: () -> Unit
){
    Card() {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 4.dp)) {
            Text(
                text = proyecto,
                style = TextStyle(fontSize = 24.sp),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(Modifier.weight(3f))
            Button(onClick = { onMasClick() }) {
                Image(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}

/**
 * Componente que contiene: botones y tareas en un LazyColumn
*/
@Composable
private fun Tareas(modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .weight(1f)
                    .height(78.dp)
            ){
                Text(text = "MIS TAREAS",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center)
                )
            }
            Spacer(Modifier.padding(4.dp))
            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .weight(1f)
                    .height(78.dp)) {
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
        /*TODO*/
    }
}

@Composable
private fun TareaCard(
    nombre: String,
    onEditarClick: () -> Unit
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
    ){
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ){
            Text(text = nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold)
            Button(onClick = { onEditarClick() }) {
                Image(imageVector = Icons.Outlined.Create,
                    contentDescription = "Editar")
                
            }
        }
    }
}

@Preview(showBackground=true)
@Composable
private fun PreviewTrabajo(){
    Android_APTheme {

    }
}