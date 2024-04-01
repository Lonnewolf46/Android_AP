package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Create
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
import com.example.android_ap.Colaborador
import com.example.android_ap.Estado
import com.example.android_ap.Tarea
import com.example.android_ap.data.TareaCampos
import com.example.android_ap.ui.popups.NuevaTarea
import com.example.android_ap.ui.popups.Warning
import com.example.android_ap.ui.theme.Android_APTheme

/**
 * Componente principal de la interfaz de Trabajo
 */
@Composable
fun TrabajoLayout(
    idProyecto: Int,
    nombreProyecto: String,
    nombreTarea: String,
    storyPoints: String,
    encargado: String,
    estado: String,
    fechaFin: String,
    listaColaboradores: List<Colaborador>,
    listaEstados: List<Estado>,
    crearTareaVisible: Boolean,
    codigoResult: Int,
    listaTareas: List<Tarea>,
    onTareasColaborador: () -> Unit,
    onTareasProyecto: () -> Unit,
    onCerrarEmergente: () -> Unit,
    onEditarTareaClick: (Int, String) -> Unit,
    onOpcionesProyectoClick: () -> Unit,
    onTareaValueChange: (TareaCampos, String) -> Unit,
    onTareaEncargadoSelectionChange: (String) -> Unit,
    onTareaEstadoSelectionChange: (String) -> Unit,
    onTareaConfirmar: () -> Unit,
    onTareaCerrarClick: () -> Unit,
    crearTarea: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProyectoActualTopBar(proyecto = nombreProyecto, onOpcionesProyectoClick)
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        TareasHeader(
            onTareasColaborador = onTareasColaborador,
            onTareasProyecto = onTareasProyecto
        )
        Column(modifier = Modifier.weight(1f)) {
            Tareas(
                idProyecto,
                listaTareas,
                codigoResult,
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
                estado = estado,
                fechaFin = fechaFin,
                listaColaboradores = listaColaboradores,
                listaEstados = listaEstados,
                codigoResult = codigoResult,
                onValueChange = onTareaValueChange,
                onEncargadoSelectionChange = onTareaEncargadoSelectionChange,
                onEstadoSelectionChange = onTareaEstadoSelectionChange,
                onConfirmar = onTareaConfirmar,
                onCerrarClick = onTareaCerrarClick,
                crearTarea = crearTarea
            )
        }

        when (codigoResult) {
            -2 -> Warning(
                texto = "Se ha producido un error inesperado. Por favor inténtelo de nuevo.",
                onClose = { onCerrarEmergente() })

            0 -> Warning(
                texto = "Tarea creada exitosamente",
                onClose = { onCerrarEmergente() })

            3 -> Warning(
                texto = "Se produjo un error de red. Verifique su conexión a internet",
                onClose = { onCerrarEmergente() })
            13 -> Warning(
                texto = "Tarea modificada satisfactoriamente.",
                onClose = { onCerrarEmergente() })
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
private fun TareasHeader(
    onTareasColaborador: () -> Unit,
    onTareasProyecto: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { onTareasColaborador() },
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
            onClick = { onTareasProyecto() },
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
    idProyecto: Int,
    listaTareas: List<Tarea>,
    codigoResult: Int,
    modifier: Modifier = Modifier,
    onEditarTareaClick: (Int, String) -> Unit
) {
    if (listaTareas.isNotEmpty()) {
        LazyColumn(Modifier.fillMaxWidth()) {
            items(listaTareas) { tarea ->
                TareaCard(
                    nombre = tarea.nombre,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onEditarClick = { onEditarTareaClick(idProyecto, tarea.nombre) })
            }
        }
    } else {
        Column {
            if (codigoResult == 3) //Si no es vacia por un error de red
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Se produjo un error de red. Verifique su conexión",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            else {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "No hay tareas",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
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

