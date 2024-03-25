package com.example.android_ap.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android_ap.data.RegistroCampos
import com.example.android_ap.ui.screens.InicioSesionLayout
import com.example.android_ap.ui.screens.MenuPrincipalLayout
import com.example.android_ap.ui.screens.ModificarInfoPersonalLayout
import com.example.android_ap.ui.screens.RegistroLayout
import com.example.android_ap.ui.screens.TrabajoLayout

enum class APScreen() {
    InicioSesion,
    Registro,
    MenuPrincipal,
    Trabajo,
    ModificarInfoPersonal
}

@Composable
fun AP_App() {

    //ViewModels
    val inicioSesionViewModel: InicioSesionViewModel = viewModel()
    val registroViewModel: RegistroViewModel = viewModel()
    val modInfoPersonalViewModel: ModificarInfoPersonalViewModel = viewModel()

    //Initialize navigation elements
    val navController: NavHostController = rememberNavController()
    var showTopBar by rememberSaveable { mutableStateOf(true) }
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = APScreen.valueOf(
        backStackEntry?.destination?.route ?: APScreen.InicioSesion.name
    )

    showTopBar = when (backStackEntry?.destination?.route) {
        //Pantallas en las que no deberia haber una barra superior
        APScreen.MenuPrincipal.name -> false
        APScreen.Trabajo.name -> false
        else -> true // en cualquier otro caso, mostrarla
    }

    showBottomBar = when (backStackEntry?.destination?.route) {
        //Pantallas en las que no deberia haber una barra inferior
        APScreen.InicioSesion.name -> false
        APScreen.Registro.name -> false
        APScreen.ModificarInfoPersonal.name -> false
        else -> true // en cualquier otro caso, mostrarla
    }


    Scaffold(
        topBar = {
        if (showTopBar) {
            APAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
                 },
        bottomBar = {
            if (showBottomBar) BottomAppBarMenu(
            { navController.popBackStack()
              navController.navigate(APScreen.MenuPrincipal.name) },
            { navController.popBackStack()
              navController.navigate(APScreen.Trabajo.name) },
            { navController.navigate(APScreen.ModificarInfoPersonal.name) })
        },

        floatingActionButton = { if (showBottomBar)
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.containerColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
            ) {
                Icon(Icons.Filled.Add, "Agregar")
            }
        })
    { innerPadding ->

        val inicioUiState by inicioSesionViewModel.uiState.collectAsState()
        val registroUiState by registroViewModel.uiState.collectAsState()
        val modInfoPersonalUiState by modInfoPersonalViewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = APScreen.InicioSesion.name, //ENTRADA
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
                            modInfoPersonalViewModel,
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

            //Modificacion de informacion personal
            composable(route = APScreen.ModificarInfoPersonal.name){
                ModificarInfoPersonalLayout(
                    telefono = modInfoPersonalUiState.telefono,
                    email = modInfoPersonalUiState.correo,
                    camposLlenos = modInfoPersonalUiState.camposLlenos,
                    onTextInput = modInfoPersonalViewModel::actualizarDatos,
                    onActualizarClicked = { /*TODO*/ }) {
                }
            }

            //Trabajo
            composable(route = APScreen.Trabajo.name){
                TrabajoLayout()
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
    modInfoPersonalViewModel: ModificarInfoPersonalViewModel,
    navController: NavController
) {
    inicioSesionViewModel.validarCampos()

    if (inicioSesionViewModel.uiState.value.camposLlenos) {

        //Llenar currentUser con valores del la BD o algo aquí
        prepModInfoPersonalData(modInfoPersonalViewModel, inicioSesionViewModel.uiState.value.usuario)

        inicioSesionViewModel.resetState()
        navController.popBackStack()
        navController.navigate(APScreen.MenuPrincipal.name)
    }
}

//Alterar el estado del viewModel para preservar la información
private fun prepModInfoPersonalData(modInfoPersonalViewModel: ModificarInfoPersonalViewModel,
                                    email: String){

    //Datos mockup
    modInfoPersonalViewModel.actualizarDatos(RegistroCampos.TELEFONO,"83035422")
    modInfoPersonalViewModel.actualizarDatos(RegistroCampos.PROYECTO,"Proyecto 1")
    modInfoPersonalViewModel.actualizarDatos(RegistroCampos.DEPARTAMENTO,"Departamento 1")
    modInfoPersonalViewModel.actualizarDatos(RegistroCampos.CORREO,email)
}