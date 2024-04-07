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
fun VerPlantillaLayout(
    titulo: String,
    listaElementos: List<String>,
    onCerrarClick: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onCerrarClick,
        modifier = Modifier.clip(RoundedCornerShape(12.dp))
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(8.dp)
            ) {
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
                            VerCard(texto = elemento, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
                TextButton(
                    onClick = onCerrarClick,
                    modifier = Modifier.align(Alignment.End)
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

@Composable
private fun VerCard(
    texto: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Text(
                text = texto,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Android_APTheme {
        //AgregarLayout("Nuevo colaborador", {},{})
    }
}