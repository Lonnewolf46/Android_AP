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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.outlined.Create
import com.example.android_ap.Tarea
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
    listaTareas: List<Tarea>,
    onEditarTareaClick: () -> Unit,
    onOpcionesProyectoClick: () -> Unit,
    onTareaValueChange: (TareaCampos, String) -> Unit,
    onTareaEncargadoSelectionChange: (String) -> Unit,
    onTareaConfirmar: () -> Unit,
    onTareaCerrarClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProyectoActualTopBar(proyecto = nombreProyecto, onOpcionesProyectoClick)
        Spacer(modifier = Modifier.padding(vertical=8.dp))
        TareasHeader()
        Column(modifier = Modifier.weight(1f)) {
            Tareas(
                listaTareas,
                Modifier.padding(16.dp),
                onEditarTareaClick
            )
        }
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
        else if (codigoResult == 0) {
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
) {
    Card() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
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

@Composable
private fun TareasHeader(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(1f)
                .height(78.dp)
        ) {
            Text(
                text = "MIS TAREAS",
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
        Spacer(Modifier.padding(4.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(1f)
                .height(78.dp)
        ) {
            Text(
                text = "TAREAS DEL PROYECTO",
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
    Divider(
        thickness = 2.dp,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

/**
 * Componente que contiene: botones y tareas en un LazyColumn
 */
@Composable
private fun Tareas(
    listaTareas: List<Tarea>,
    modifier: Modifier = Modifier,
    onEditarTareaClick: () -> Unit
) {
    if (listaTareas.isNotEmpty()) {
        LazyColumn(Modifier.fillMaxWidth()) {
            items(listaTareas) { tarea ->
                TareaCard(
                    nombre = tarea.nombre,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onEditarClick = { onEditarTareaClick() })
            }
        }
    } else {
        Column {
            TareaCard(
                nombre = "No hay tareas",
                modifier = Modifier
                    .fillMaxWidth(),
                onEditarClick = {}
            )
        }
    }
}

@Composable
private fun TareaCard(
    nombre: String,
    onEditarClick: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .padding(8.dp)
        ) {
            Text(
                text = nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Button(onClick = { onEditarClick() }) {
                Image(
                    imageVector = Icons.Outlined.Create,
                    contentDescription = "Editar"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTrabajo() {
    Android_APTheme {


    }
}

