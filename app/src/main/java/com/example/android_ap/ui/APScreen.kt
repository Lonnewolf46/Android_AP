package com.example.android_ap.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android_ap.R
import com.example.android_ap.data.InicioSesionUiState
import com.example.android_ap.data.TempNewsData
import com.example.android_ap.ui.screens.InfoProyecto
import com.example.android_ap.ui.screens.InicioSesionLayout
import com.example.android_ap.ui.screens.MenuPrincipalLayout
import com.example.android_ap.ui.screens.Noticias
import com.example.android_ap.ui.screens.RegistroLayout
import com.example.android_ap.ui.screens.SearchBar

enum class APScreen() {
    InicioSesion,
    Registro,
    MenuPrincipal
}

@Composable
fun AP_App() {
    val inicioSesionViewModel: InicioSesionViewModel = viewModel()
    val registroViewModel: RegistroViewModel = viewModel()

    //Initialize navigation elements
    val navController: NavHostController = rememberNavController()
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = APScreen.valueOf(
        backStackEntry?.destination?.route ?: APScreen.InicioSesion.name
    )
    showBottomBar = when (backStackEntry?.destination?.route) {
        APScreen.InicioSesion.name -> false // on this screen bottom bar should be hidden
        APScreen.Registro.name -> false // here too
        else -> true // in all other cases show bottom bar
    }


    Scaffold(topBar = {
        APAppBar(
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() })
    },
        bottomBar = { if (showBottomBar) BottomAppBarMenu() }) { innerPadding ->

        val inicioUiState by inicioSesionViewModel.uiState.collectAsState()
        val registroUiState by registroViewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = APScreen.InicioSesion.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            //Inicio de Sesion
            composable(route = APScreen.InicioSesion.name) {
                InicioSesionLayout(
                    inicioUiState.usuario,
                    inicioUiState.clave,
                    inicioUiState.claveVisible,
                    inicioUiState.camposLlenos,
                    inicioUiState.primerInicio,
                    onTextInput = inicioSesionViewModel::actualizarInfo,
                    onViewPassword = { inicioSesionViewModel.verClave(it) },
                    onIniciarSesionClicked = {
                        LoginToStart(
                            inicioSesionViewModel,
                            inicioUiState,
                            navController
                        )
                    },
                    onRegistroTextClicked = { navController.navigate(APScreen.Registro.name) },
                    onDialogClose = inicioSesionViewModel::cerrarEmergente
                )
            }

            //Registro
            composable(route = APScreen.Registro.name) {
                RegistroLayout(
                    nombre = registroUiState.nombre,
                    cedula = registroUiState.cedula,
                    telefono = registroUiState.telefono,
                    email = registroUiState.correo,
                    clave = registroUiState.clave,
                    passwordVisible = registroUiState.claveVisible,
                    camposLlenos = registroUiState.camposLlenos,
                    onTextInput = registroViewModel::actualizarDatos,
                    onViewPassword = { registroViewModel.verClave(it) },
                    onInicioSesionTextClicked = { navController.navigateUp() },
                    onRegistroClicked = registroViewModel::validarCampos,
                    onDialogClose = registroViewModel::cerrarEmergente
                )
            }

            //Menu principal
            composable(route = APScreen.MenuPrincipal.name) {
                MenuPrincipalLayout()
            }
        }
    }
}

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
fun BottomAppBarMenu() {
    BottomAppBar(
        actions = {
            BottomAppBarIcon(onClick = {}, texto = "Incio", image = Icons.Filled.Home)

            BottomAppBarIcon(onClick = {}, texto = "Trabajo", image = Icons.Filled.List)

            BottomAppBarIcon(onClick = {}, texto = "Notificaciones", image = Icons.Filled.Notifications)

            BottomAppBarIcon(onClick = {}, texto = "Más", image = Icons.Filled.Menu)

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    )
}

@Composable
fun BottomAppBarIcon(onClick: () -> Unit,texto: String, image: ImageVector){
    Card() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
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

private fun LoginToStart(
    inicioSesionViewModel: InicioSesionViewModel,
    inicioUiState: InicioSesionUiState,
    navController: NavController
) {
    inicioSesionViewModel.validarCampos()

    if (inicioSesionViewModel.uiState.value.camposLlenos) {
        inicioSesionViewModel.resetState()
        navController.popBackStack()
        navController.navigate(APScreen.MenuPrincipal.name)
    }

}