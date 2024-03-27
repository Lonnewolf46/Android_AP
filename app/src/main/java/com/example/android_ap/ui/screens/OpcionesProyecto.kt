package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.R
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun OpcionesLayout(
    onForoClick: () -> Unit,
    onReunionesClick: () -> Unit,
    onInformeClick: () -> Unit,
    onBurndownClick: () -> Unit
){
    Column(verticalArrangement = Arrangement.Center
        ,modifier = Modifier.fillMaxSize()){
        Column(verticalArrangement = Arrangement.spacedBy(36.dp)) {
            OpcionCard(
                image = ImageVector.vectorResource(R.drawable.forum_icon),
                texto = "Foro de Proyecto",
                modifier = Modifier.fillMaxWidth(),
                onForoClick
            )
            OpcionCard(
                image = ImageVector.vectorResource(R.drawable.discussion_meeting),
                texto = "Reuniones",
                modifier = Modifier.fillMaxWidth(),
                onReunionesClick
            )
            OpcionCard(
                image = ImageVector.vectorResource(R.drawable.general_report),
                texto = "Informe General",
                modifier = Modifier.fillMaxWidth(),
                onInformeClick
            )
            OpcionCard(
                image = ImageVector.vectorResource(R.drawable.burndown_icon),
                texto = "Burndown Chart",
                modifier = Modifier.fillMaxWidth(),
                onBurndownClick
            )
        }
    }
}

@Composable
private fun OpcionCard(image: ImageVector, texto: String,
               modifier: Modifier,
               onClick: () -> Unit){
    Card(modifier = modifier){
        Row(verticalAlignment = Alignment.CenterVertically){
            Button(onClick = { onClick() },
                modifier = Modifier
                    .height(75.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Image(imageVector = image,
                    contentDescription = null
                )
            }
            ClickableText(text = AnnotatedString(text = texto),
                style = TextStyle(fontSize = 24.sp,
                    fontWeight = FontWeight.Bold)
            ){onClick()}
        }
    }
}

@Preview(showBackground=true)
@Composable
private fun OpcionesPreview(){
    Android_APTheme {
    }
}