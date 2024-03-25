package com.example.android_ap.ui.UIAuxiliar

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.android_ap.ui.theme.Android_APTheme

enum class FABState(){
    EXPANDED,
    COLLAPSED
}

class MinFabItem(
    val icon: Int,
    val label: String,
    val path: () -> Unit
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun APAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {},
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Retroceso"
                    )
                }
            }
        }
    )
}

@Composable
fun BottomAppBarMenu(onInicioClick: () -> Unit,
                     onTrabajoClick: () -> Unit,
                     onMasClick: () -> Unit) {
    BottomAppBar(
        actions = {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly
            ) {
                BottomAppBarIcon(onClick = onInicioClick,texto = "Inicio",image = Icons.Filled.Home)
                BottomAppBarIcon(onClick = onTrabajoClick, texto = "Trabajo", image = Icons.Filled.List)
                BottomAppBarIcon(onClick = {}, texto = "Avisos", image = Icons.Filled.Notifications)
                BottomAppBarIcon(onClick = onMasClick, texto = "Usuario", image = Icons.Filled.Person)
            }

        }
    )
}

@Composable
fun BottomAppBarIcon(onClick: () -> Unit,texto: String, image: ImageVector){
    Card() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(4.dp)) {
            IconButton(onClick = { onClick() }) {
                Icon(
                    image,
                    contentDescription = texto,
                )
            }
            Text(text = texto)
        }
    }
}

@Composable
fun FABMenuPrincipal(buttonState: FABState,
                     onClick: (FABState) -> Unit,
                     items: List<MinFabItem>){
    val transition = updateTransition(targetState = buttonState, label = "transition")
    val rotate by transition.animateFloat(label = "rotate") {
        if (it == FABState.EXPANDED) 315f else 1f
    }

    val fabScale by transition.animateFloat(label = "FABScale") {
        if (it==FABState.EXPANDED) 36f else 0f
    }

    val alpha by transition.animateFloat(label = "alpha", transitionSpec = {tween(durationMillis = 50)})
    {
        if(it == FABState.EXPANDED) 1f else 0f
    }

    val textShadow by transition.animateDp(label = "textShadow", transitionSpec = {tween(durationMillis = 50)})
    {
        if(it == FABState.EXPANDED) 2.dp else 0.dp
    }
    

    Column(horizontalAlignment = Alignment.End) {

        if(transition.currentState == FABState.EXPANDED){
            items.forEach { 
                MinFab(
                    item = it,
                    alpha = alpha,
                    textShadow = textShadow,
                    fabScale = fabScale,
                    onClick = {})
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }

        FloatingActionButton(
            onClick = {
                onClick(
                    if (transition.currentState == FABState.EXPANDED) FABState.COLLAPSED
                    else FABState.EXPANDED
                )
            },
            containerColor = BottomAppBarDefaults.containerColor,
            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
            modifier = Modifier.shadow(elevation = 4.dp, shape = CircleShape)
        ) {
            Icon(
                Icons.Filled.Add, "Agregar",
                modifier = Modifier.rotate(rotate)
            )
        }
    }
}


@Composable
fun MinFab(
    item: MinFabItem,
    alpha: Float,
    textShadow: Dp,
    fabScale: Float,
    showLabel: Boolean = true,
    onClick: (MinFabItem) -> Unit
){
    val buttonColor = Color(0xFF0D60B5)
    val painter: Painter = painterResource(item.icon)
    val shadow = Color.Black.copy(0.5f)

    Row {
        if(showLabel) {
            Text(
                text = item.label,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alpha(
                    animateFloatAsState(
                        targetValue = alpha,
                        animationSpec = tween(50)
                    ).value
                )
                    .shadow(textShadow, shape = RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 4.dp, vertical = 8.dp)
            )

            Spacer(Modifier.padding(4.dp))
        }

        Canvas(
            modifier = Modifier
                .size(44.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    onClick = { onClick.invoke(item) },
                    indication = rememberRipple(
                        bounded = false,
                        radius = 40.dp,
                        color = BottomAppBarDefaults.containerColor
                    )
                )
        ) {

            drawCircle(
                color = shadow,
                radius = fabScale
            )

            drawCircle(
                color = buttonColor,
                radius = fabScale*2
            )
            translate(left = fabScale, top = fabScale) {
                with(painter) {
                    draw(
                        size = Size(fabScale*2, fabScale*2),
                        alpha = alpha
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemsPreview(){
    Android_APTheme {
        //MinFab(item = MinFabItem(R.drawable.forum_icon,"",""), {})
    }
}