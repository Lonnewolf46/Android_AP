package com.example.android_ap.ui.popups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.BaseClass
import com.example.android_ap.Colaborador
import com.example.android_ap.ui.theme.Android_APTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T: BaseClass> AgregarPlantillaLayout(
    titulo: String,
    listaElementos: List<T>,
    listaElegidos: List<Int>,
    onAsignarQuitarClick: (Int) -> Unit,
    onCerrarClick: () -> Unit){

    AlertDialog(
        onDismissRequest = onCerrarClick,
        modifier = Modifier.clip(RoundedCornerShape(12.dp))
    ) {
        Surface(modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(8.dp)) {
                Text(
                    text = titulo,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                if (listaElementos.isNotEmpty()) {

                    LazyColumn(Modifier.fillMaxWidth()) {
                        items(listaElementos) { elemento ->
                            when(elemento){
                                is Colaborador -> {
                                    val exists = listaElegidos.contains(elemento.id)
                                    if (!exists) {
                                        AgregarCard(
                                            nombre = elemento.nombre,
                                            textButton = "Asignar",
                                            modifier = Modifier.fillMaxWidth(),
                                            onActionClick = { onAsignarQuitarClick(elemento.id) }
                                        )
                                    }
                                    else{
                                        AgregarCard(
                                            nombre = elemento.nombre,
                                            textButton = "Quitar",
                                            modifier = Modifier.fillMaxWidth(),
                                            onActionClick = { onAsignarQuitarClick(elemento.id) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                TextButton(onClick = onCerrarClick,
                    modifier = Modifier.align(Alignment.End)) {
                    Text(text = "Cerrar",
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Composable
fun AgregarCard(nombre: String,
                textButton: String,
                modifier: Modifier,
                onActionClick: () -> Unit
){
    Card(modifier = modifier
        .padding(4.dp)
        ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ){
            Text(text = nombre,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f))
                Button(
                    onClick = onActionClick,
                    //colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2962FF)),
                    modifier = Modifier
                ) {
                    Text(text = textButton,
                        textAlign = TextAlign.Center)
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview(){
    Android_APTheme {
        //AgregarLayout("Nuevo colaborador", {},{})
    }
}