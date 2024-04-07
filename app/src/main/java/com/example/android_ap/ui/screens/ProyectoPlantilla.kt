package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.android_ap.Colaborador
import com.example.android_ap.Estado
import com.example.android_ap.R
import com.example.android_ap.Tarea
import com.example.android_ap.data.ProyectoCampos
import com.example.android_ap.data.TareaCampos
import com.example.android_ap.ui.UIAuxiliar.CustomExposedDropdownMenuBox
import com.example.android_ap.ui.popups.AgregarPlantillaLayout
import com.example.android_ap.ui.popups.NuevaTarea
import com.example.android_ap.ui.popups.VerPlantillaLayout
import com.example.android_ap.ui.popups.Warning
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun ProyectoPlantillaLayout(
    imagen: ImageVector,
    titulo: String,
    nombre: String,
    recursos: String,
    presupuesto: String,
    estado: String,
    descripcion: String,
    responsable: String,
    codigoResult: Int,
    listaEstadosProyecto: List<Estado>,
    listaEstadosTarea: List<Estado>,
    listaColaboradores: List<Colaborador>,
    listaColaboradoresElegidos: List<Int>,
    onValueChange: (ProyectoCampos, String) -> Unit,
    onEstadoProyectoSelection: (String) -> Unit,
    onResponsableProyectoSelection: (String) -> Unit,
    onAsignarColaboradores: () -> Unit,
    onAgregarQuitarColaborador: (Int) -> Unit,
    onCrearTareas: () -> Unit,
    onCrearProyecto: () -> Unit,
    onCerrarPopUp: () -> Unit,
    nombreTarea: String,
    storyPointsTarea: String,
    fechaFinTarea: String,
    encargadoTarea: String,
    estadoTarea: String,
    onCamposTareaValueChange: (TareaCampos, String) -> Unit,
    onEncargadoTareaSelection: (String) -> Unit,
    onEstadoTareaSelection: (String) -> Unit,
    onConfirmarAgregarTarea: () -> Unit,
    codigoResultTarea: Int,
    crearProyecto: Boolean,
    listaTareas: List<Tarea> = listOf()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize()
    ) {
        Image(
            imageVector = imagen,
            contentDescription = null,
            modifier = Modifier.height(108.dp)
        )
        Text(
            text = titulo,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        DatosProyecto(
            nombre = nombre,
            recursos = recursos,
            presupuesto = presupuesto,
            estado = estado,
            descripcion = descripcion,
            responsable = responsable,
            listaEstadosProyecto = listaEstadosProyecto,
            listaColaboradores = listaColaboradores.filter { it.id in listaColaboradoresElegidos },
            onValueChange = onValueChange,
            onEstadoSelection = onEstadoProyectoSelection,
            onResponsableSelection = onResponsableProyectoSelection,
            onAsignarColaboradores = onAsignarColaboradores,
            onCrearTareas = onCrearTareas,
            onCrearProyecto = onCrearProyecto,
            modifier = Modifier.fillMaxWidth(),
            crearProyecto = crearProyecto
        )

        when (codigoResult) {
            -2 -> Warning(
                texto = stringResource(R.string.unexpected_error_message),
                onClose = onCerrarPopUp
            )

            0 -> Warning(
                texto = "Proceso exitoso.",
                onClose = onCerrarPopUp
            )

            1 -> Warning(
                texto = "Se requiere llenar todos los campos",
                onClose = onCerrarPopUp
            )

            3 -> Warning(
                texto = stringResource(R.string.error_red_message),
                onClose = onCerrarPopUp
            )

            16 -> AgregarPlantillaLayout(
                titulo = "Elegir colaboradores",
                listaElementos = listaColaboradores,
                listaElegidos = listaColaboradoresElegidos,
                onAsignarQuitarClick = onAgregarQuitarColaborador,
                onCerrarClick = onCerrarPopUp
            )

            17 -> NuevaTarea(
                nombre = nombreTarea,
                storyPoints = storyPointsTarea,
                encargado = encargadoTarea,
                estado = estadoTarea,
                fechaFin = fechaFinTarea,
                listaColaboradores = listaColaboradores.filter { it.id in listaColaboradoresElegidos },
                listaEstados = listaEstadosTarea,
                onValueChange = onCamposTareaValueChange,
                onEncargadoSelectionChange = onEncargadoTareaSelection,
                onEstadoSelectionChange = onEstadoTareaSelection,
                codigoResult = codigoResultTarea,
                onConfirmar = onConfirmarAgregarTarea,
                onEliminar = { /*TODO*/ },
                onCerrarClick = onCerrarPopUp,
                crearTarea = true
            )

            18 -> Warning(
                texto = "Tarea agregada satisfactoriamente",
                onClose = onCerrarPopUp
            )

            19 -> Warning(
                texto = "Se requiere de, al menos, una tarea.",
                onClose = onCerrarPopUp
            )

            20 -> VerPlantillaLayout(
                titulo = "Colaboradores",
                listaElementos = listaColaboradores
                    .filter { it.id in listaColaboradoresElegidos }
                    .map { it.nombre },
                onCerrarClick = onCerrarPopUp
            )

            21 -> VerPlantillaLayout(
                titulo = "Tareas",
                listaElementos = listaTareas.map { it.nombre },
                onCerrarClick = onCerrarPopUp
            )
        }

    }

}

@Composable
fun DatosProyecto(
    nombre: String,
    recursos: String,
    presupuesto: String,
    estado: String,
    descripcion: String,
    responsable: String,
    listaEstadosProyecto: List<Estado>,
    listaColaboradores: List<Colaborador>,
    onValueChange: (ProyectoCampos, String) -> Unit,
    onEstadoSelection: (String) -> Unit,
    onResponsableSelection: (String) -> Unit,
    onAsignarColaboradores: () -> Unit,
    onCrearTareas: () -> Unit,
    onCrearProyecto: () -> Unit,
    modifier: Modifier,
    crearProyecto: Boolean
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        //Nombre
        OutlinedTextField(
            value = nombre,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onValueChange(ProyectoCampos.NOMBRE, it) },
            label = { Text(text = "NOMBRE") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            modifier = modifier
        )

        //Recursos
        OutlinedTextField(
            value = recursos,
            singleLine = false,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onValueChange(ProyectoCampos.RECURSOS, it) },
            label = { Text(text = "RECURSOS") },
            modifier = modifier
        )

        //Presupuesto
        OutlinedTextField(
            value = presupuesto,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = {
                if (it.isDigitsOnly())
                    onValueChange(ProyectoCampos.PRESUPUESTO, it)
            },
            label = { Text(text = "PRESUPUESTO") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = modifier
        )

        Button(
            onClick = { onAsignarColaboradores() },
            modifier = modifier
        ) {
            Text(
                text =  if(crearProyecto) "Asignar colaboradores" else "Ver colaboradores",
                fontSize = 20.sp,
            )
        }
        Button(
            onClick = { onCrearTareas() },
            modifier = modifier
        ) {
            Text(
                text = if(crearProyecto) "Crear tareas" else "Ver tareas",
                fontSize = 20.sp,
            )
        }

        //Estado del proyecto
        CustomExposedDropdownMenuBox(
            titulo = "ESTADO DEL PROYECTO",
            seleccionado = estado,
            listaElementos = listaEstadosProyecto.map { it.estado },
            onValueChange = { onEstadoSelection(it) })

        //Descripcion
        OutlinedTextField(
            value = descripcion,
            singleLine = false,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onValueChange(ProyectoCampos.DESCRIPCION, it) },
            label = { Text(text = "DESCRIPCIÓN") },
            modifier = modifier
        )

        //Responsable del proyecto
        CustomExposedDropdownMenuBox(
            titulo = "RESPONSABLE",
            seleccionado = responsable,
            listaElementos = listaColaboradores.map { it.nombre },
            onValueChange = { onResponsableSelection(it) })

        Button(
            onClick = { onCrearProyecto() },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = if(crearProyecto) "Crear proyecto" else "Modificar proyecto",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProyecto() {
    Android_APTheme {
    }
}