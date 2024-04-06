package com.example.android_ap.ui.popups

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.android_ap.data.TareaCampos
import com.example.android_ap.ui.UIAuxiliar.CustomExposedDropdownMenuBox
import com.example.android_ap.ui.theme.Android_APTheme
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaTarea(nombre: String,
               storyPoints: String,
               encargado: String,
               estado: String,
               fechaFin: String,
               listaColaboradores: List<Colaborador>,
               listaEstados: List<Estado>,
               onValueChange: (TareaCampos, String) -> Unit,
               onEncargadoSelectionChange: (String) -> Unit,
               onEstadoSelectionChange: (String) -> Unit,
               codigoResult: Int,
               onConfirmar: () -> Unit,
               onEliminar: () -> Unit,
               onCerrarClick: () -> Unit,
               crearTarea: Boolean){
    AlertDialog(
        onDismissRequest = onCerrarClick,
        modifier = Modifier.clip(RoundedCornerShape(12.dp))
    ){
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(8.dp)
            ){
                Text("Tareas de Proyecto",
                    style = TextStyle(fontSize = 20.sp),
                    fontWeight = FontWeight.Bold
                )

                //Nombre tarea
                OutlinedTextField(
                    value = nombre,
                    singleLine = true,
                    isError = (codigoResult==8),
                    supportingText = {if(codigoResult==8) Text(text = "El campo no puede estar vacío")},
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    onValueChange = { onValueChange(TareaCampos.NOMBRE, it) },
                    label = { Text(text = "NOMBRE DE TAREA") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )

                //Story Points
                OutlinedTextField(
                    value = storyPoints,
                    singleLine = true,
                    isError = (codigoResult==9),
                    supportingText = {if(codigoResult==9) Text(text = "El campo no puede estar vacío")},
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    onValueChange = {
                        if (it.length <= 2 && it.isDigitsOnly()) {
                            onValueChange(TareaCampos.STORYPOINTS, it) }
                    },
                    label = { Text(text = "STORY POINTS") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                CustomExposedDropdownMenuBox(
                    titulo= "ENCARGADO",
                    seleccionado = encargado,
                    listaElementos = listaColaboradores.map { it.nombre },
                    onValueChange = { onEncargadoSelectionChange(it) })

                when(codigoResult){
                    10 -> Text(text = "Se requiere de un encargado",
                        fontSize = 8.sp,
                        color = Color.Red)
                }

                CustomExposedDropdownMenuBox(
                    titulo= "ESTADO",
                    seleccionado = estado,
                    listaElementos = listaEstados.map { it.estado },
                    onValueChange = { onEstadoSelectionChange(it) })

                when(codigoResult){
                    3 -> Text(text = "Error recuperando la información. Verifque su conexión.",
                        fontSize = 8.sp,
                        color = Color.Red)
                    11 -> Text(text = "Se requiere de un estado",
                        fontSize = 8.sp,
                        color = Color.Red)
                }

                // Obteniendo contexto local
                val mContext = LocalContext.current
                // inicializando calendario
                val mCalendar = Calendar.getInstance()

                // Recuperando fecha, mes y dia actual
                val mYear = mCalendar.get(Calendar.YEAR)
                val mMonth = mCalendar.get(Calendar.MONTH)
                val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

                mCalendar.time = Date()
                val mEndDatePickerDialog = DatePickerDialog(
                    mContext,
                    { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                        onValueChange(TareaCampos.FECHAFIN,"$mYear-${mMonth + 1}-$mDayOfMonth")
                    }, mYear, mMonth, mDay
                )

                OutlinedTextField(
                    value = fechaFin,
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { mEndDatePickerDialog.show() }) {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = "Expander"
                            )
                        }
                    },
                    label = { Text(text = "FECHA FIN") },
                    onValueChange = {}
                )
                when(codigoResult){
                    12 -> Text(text = "Se requiere de una fecha de finalización",
                        fontSize = 8.sp,
                        color = Color.Red)
                }

                Button(onClick = onConfirmar,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(text = if(crearTarea) "Crear tarea" else "Modificar Tarea",
                        style = TextStyle(fontSize = 16.sp),
                        fontWeight = FontWeight.Bold
                        )

                }
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom) {
                    if(!crearTarea)
                        Button(onClick = onEliminar,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF295C))) {
                            Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                        }
                    TextButton(
                        onClick = onCerrarClick,
                        modifier = Modifier.align(alignment = Alignment.Bottom)
                    ) {
                        Text(
                            text = "Cerrar",
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground=true)
@Composable
fun NuevaTareaPreview(){
    Android_APTheme {
    }
}