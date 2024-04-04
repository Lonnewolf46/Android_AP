package com.example.android_ap.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.Colaborador
import com.example.android_ap.Proyecto
import com.example.android_ap.R
import com.example.android_ap.ui.popups.AgregarPlantillaLayout
import com.example.android_ap.ui.popups.Warning
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun ColaboradoresLayout(
    listaColaboradores: List<Colaborador>,
    listaProyectos: List<Proyecto>,
    codigoPopUp: Int,
    codigoResult: Int,
    onProyectoSelection: (Int) -> Unit,
    onReasignarProyectoSelected: (Int) -> Unit,
    onAlternarProyectoPopUp: () -> Unit,
    onAlternarEliminarColaborador: () -> Unit,
    onCerrarPopups: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        ColaboradoresHeader()

        Column(Modifier.weight(1f)) {
            ColaboradoresContent(
                listaColaboradores = listaColaboradores,
                onReasignarClick = {
                    onReasignarProyectoSelected(it)
                    onAlternarProyectoPopUp()
                                   },
                onEliminarClick = onAlternarEliminarColaborador
            )
        }
    }
    when(codigoPopUp){
        1 -> AgregarPlantillaLayout(
            titulo = "Reasignación de proyecto",
            listaElementos = listaProyectos,
            listaElegidos = listOf(),
            onAsignarQuitarClick = {
                onProyectoSelection(it)
                onAlternarProyectoPopUp()
                                   },
            onCerrarClick = { onAlternarProyectoPopUp() }
        )
        2 -> Warning(
            texto = "No disponible actualmente.",
            onClose = { onAlternarEliminarColaborador() })
    }

    when(codigoResult){
        -2 -> Warning(
            texto = stringResource(R.string.unexpected_error_message),
            onClose = onCerrarPopups )
        0 -> Warning(
            texto = "Actualización exitosa.",
            onClose = onCerrarPopups )
        3 -> Warning(
            texto = stringResource(R.string.error_red_message),
            onClose = onCerrarPopups )
    }
}

@Composable
private fun ColaboradoresHeader() {
    Text(
        text = "Colaboradores",
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    )

}


@Composable
private fun ColaboradoresContent(
    listaColaboradores: List<Colaborador>,
    onReasignarClick: (Int) -> Unit,
    onEliminarClick: () -> Unit
) {

    if (listaColaboradores.isNotEmpty()) {
        LazyColumn(Modifier.fillMaxWidth()) {
            items(listaColaboradores) { colaborador ->
                ColaboradorCard(
                    nombre = colaborador.nombre,
                    modifier = Modifier.fillMaxWidth(),
                    onReasignarClick = { onReasignarClick( colaborador.id) },
                    onEliminarClick = onEliminarClick )
            }
        }
    } else Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "No hay colaboradores para mostrar, puede ser debido a un error de red.",
            modifier = Modifier.padding(8.dp))
    }
}

@Composable
private fun ColaboradorCard(
    nombre: String,
    modifier: Modifier,
    onReasignarClick: () -> Unit,
    onEliminarClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(4.dp)
        ) {
            Text(
                text = nombre,
                style = TextStyle(
                    fontSize = 16.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onReasignarClick,
                    //colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2962FF)),
                    modifier = Modifier
                ) {
                    Text(
                        text = "Reasignar",
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = onEliminarClick ,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF295C))
                ) {
                    Text(
                        text = "Eliminar",
                        textAlign = TextAlign.Center,
                        color = Color(0xFFFFFFFF)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Android_APTheme {
        //ColaboradoresLayout(, {},{},{})
    }
}