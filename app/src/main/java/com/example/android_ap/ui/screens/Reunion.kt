package com.example.android_ap.ui.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.Colaborador
import com.example.android_ap.R
import com.example.android_ap.data.ReunionCampos
import com.example.android_ap.ui.popups.AgregarPlantillaLayout
import com.example.android_ap.ui.popups.Warning
import com.example.android_ap.ui.theme.Android_APTheme
import java.util.Calendar
import java.util.Date

@Composable
fun ReunionLayout(
    tema: String,
    fecha: String,
    medio: String,
    formato: String,
    detalles: String,
    verAsignar: Boolean,
    codigoResult: Int,
    listaColaboradores: List<Colaborador>,
    listaColaboradoresElegidos: List<Int>,
    onAlternarAsignar: () -> Unit,
    onInfoWindowClose: () -> Unit,
    onValueChange: (ReunionCampos, String) -> Unit,
    onAsignarColaboradores: (Int) -> Unit,
    onCrearReunion: () -> Unit,
){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)){

        Image(imageVector = ImageVector.vectorResource(R.drawable.meeting),
            contentDescription = null,
            modifier = Modifier.height(108.dp))

        ReunionCampos(
            tema = tema,
            fecha = fecha,
            medio = medio,
            formato = formato,
            detalles = detalles,
            onValueChange = onValueChange,
            onAlternarAsignar = onAlternarAsignar,
            onCrearReunion = onCrearReunion,
            modifier = Modifier.fillMaxSize())
    }
    if(verAsignar){
        AgregarPlantillaLayout(
            titulo = "Asignar colaborador",
            listaElementos = listaColaboradores,
            listaElegidos = listaColaboradoresElegidos,
            onAsignarQuitarClick = onAsignarColaboradores,
            onCerrarClick = { onAlternarAsignar() }
        )
    }

    else{
        when(codigoResult){
            -2 ->  Warning(
                texto = "Se ha producido un error inesperado. Por favor inténtelo de nuevo.",
                onClose = onInfoWindowClose )

            0 -> Warning(
                texto = "Reunión creada",
                onClose = onInfoWindowClose )

            1 -> Warning(
                texto = "Se requiere llenar todos los campos",
                onClose = onInfoWindowClose )

            3 ->  Warning(
                texto = "Se produjo un error de red. Verifique su conexión a internet",
                onClose = onInfoWindowClose )
            15 -> Warning(
                texto = "Asigne la reunión como mínimo a un colaborador.",
                onClose = onInfoWindowClose )
        }
    }

}

@Composable
private fun ReunionCampos(
    tema: String,
    fecha: String,
    medio: String,
    formato: String,
    detalles: String,
    onAlternarAsignar: () -> Unit,
    onValueChange: (ReunionCampos, String) -> Unit,
    onCrearReunion: () -> Unit,
    modifier: Modifier = Modifier){

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier) {

        Text(text="Nueva reunión",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start),
        )

        OutlinedTextField(
            value = tema,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onValueChange(ReunionCampos.TEMA, it) },
            label = { Text(text = "TEMA") },
            placeholder = { Text("Tema de la reunión") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )

        // Obteniendo contexto local
        val mContext = LocalContext.current

        // inicializando calendario
        val mCalendar = Calendar.getInstance()

        // Recuperando fecha, mes y dia actual
        val mYear = mCalendar.get(Calendar.YEAR)
        val mMonth = mCalendar.get(Calendar.MONTH)
        val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                onValueChange(ReunionCampos.FECHA,"$mYear-${mMonth + 1}-$mDayOfMonth")
            }, mYear, mMonth, mDay
        )

        OutlinedTextField(
            value = fecha,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { mDatePickerDialog.show() }) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Expander"
                    )
                }
            },
            label = { Text(text = "FECHA") },
            onValueChange = {}
        )

        OutlinedTextField(
            value = medio,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onValueChange(ReunionCampos.MEDIO, it) },
            label = { Text(text = "MEDIO") },
            placeholder = { Text("Medio de la reunión") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = formato,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onValueChange(ReunionCampos.FORMATO, it) },
            label = { Text(text = "FORMATO") },
            placeholder = { Text("Formato de la reunión") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = detalles,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { onValueChange(ReunionCampos.DETALLES, it) },
            label = { Text(text = "ENLACE/UBICACIÓN") },
            placeholder = { Text("Ubicación de la reunión") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )

        Button(
            onClick = { onAlternarAsignar() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(vertical=4.dp)
        ) {
            Text(
                text = "Asignar colaboradores",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = { onCrearReunion() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(vertical=4.dp)
        ) {
            Text(
                text = "Crear reunión",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground=true)
@Composable
private fun Preview(){
    Android_APTheme {
        //ReunionLayout()
    }
}