package com.example.android_ap.ui.popups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.example.android_ap.ui.theme.Android_APTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarPlantillaLayout(
    titulo: String,
    /*TODO: Recibir lista de objetos, ya sea colaboradores o proyectos*/
    onAsignarClick: () -> Unit,
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
                /*TODO: Incorporar LazyColumn*/
                //MOCKUP
                AgregarCard(nombre = "Item 1", onAsignarClick = onAsignarClick)

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
                onAsignarClick: () -> Unit
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ){
            Text(text = nombre,
                style = TextStyle(
                    fontSize = 16.sp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onAsignarClick() },
                    //colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2962FF)),
                    modifier = Modifier
                ) {
                    Text(text = "Asignar",
                        textAlign = TextAlign.Center)
                }
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