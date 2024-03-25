package com.example.android_ap.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_ap.R
import com.example.android_ap.data.TempNewsData
import com.example.android_ap.ui.theme.Android_APTheme

@Composable
fun MenuPrincipalLayout() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(16.dp)) {
            SearchBar(modifier = Modifier.padding(8.dp))

            val strings = TempNewsData().getNews()
            Noticias(
                noticias = strings,
                modifier = Modifier.padding(12.dp)
            )
        }
        InfoProyecto(
            proyecto = "Diseño de la página principal",
            modifier = Modifier.weight(1f))
    }
}


@Composable
fun Noticias(
    modifier: Modifier,
    noticias: List<String>
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Noticias",
            style = TextStyle(
                fontSize = 39.sp,
                fontWeight = FontWeight.ExtraBold
            )
        )
        Card(
            shape = RoundedCornerShape(size = 15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFD9D9D9), // Set your desired background color
            )
        ) {
            LazyColumn() {
                items(noticias) { texto ->
                    NoticiasCard(texto = texto, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun NoticiasCard(texto: String, modifier: Modifier) {
    Card(shape = RoundedCornerShape(size = 15.dp), modifier = modifier) {
        Text(text = texto, modifier)
    }
}

@Composable
fun SearchBar(modifier: Modifier) {
    Card(
        modifier = modifier
            .height(64.dp)
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = modifier.clip(RoundedCornerShape(16.dp))) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_background),
                    contentDescription = null
                )
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.app_logo_vct),
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp)
                )
            }
            TextField(
                value = "",
                singleLine = true,
                onValueChange = {},
                placeholder = { Text("Buscar") },
                modifier = modifier
            )

            Box(
                modifier = modifier
                    .alpha(0.2f)
                    .width(55.dp)
                    .height(55.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(size = 15.dp)
                    )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.searchicon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}

@Composable
fun InfoProyecto(proyecto: String,modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 180.dp
                )
            )
    ) {
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "PROYECTO ASIGNADO",
                style = TextStyle(
                    fontSize = 39.sp,
                    fontWeight = FontWeight(800),
                    shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(0.0f, 10.0f),
                        blurRadius = 7f
                    )
                )
            )
            Spacer(Modifier.padding(vertical = 16.dp))

            Card(modifier = modifier
                .height(36.dp)){
                Text(text = proyecto,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .height(32.dp)
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMenu() {
    Android_APTheme {
    }
}