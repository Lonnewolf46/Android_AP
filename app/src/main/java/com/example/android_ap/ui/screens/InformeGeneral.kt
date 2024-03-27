package com.example.android_ap.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InformeGeneralLayout(){
    Column(Modifier.padding(8.dp)) {
        Text(
            text = "Informe General",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical=8.dp)
        )
        Estadisticas()
    }
}

@Composable
private fun Estadisticas(){
    Column(){
        Demo_ExposedDropdownMenuBox("PROYECTO")

        /*TODO*/
        Text(text = "GRÁFICA DE INFORME GENERAL AQUI")
    }
}