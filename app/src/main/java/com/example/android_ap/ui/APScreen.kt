package com.example.android_ap.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android_ap.R
import com.example.android_ap.ui.screens.InicioSesionLayout
import com.example.android_ap.ui.screens.RegistroLayout

enum class APScreen() {
    InicioSesion,
    Registro
}

@Composable
fun AP_App() {
    val inicioSesionViewModel: InicioSesionViewModel = viewModel()
    val registroViewModel: RegistroViewModel = viewModel()

    //Initialize navigation elements
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = APScreen.valueOf(
        backStackEntry?.destination?.route ?: APScreen.InicioSesion.name
    )

    Scaffold(topBar = {
        APAppBar(
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() })
    }) { innerPadding ->

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
                    onTextInput = inicioSesionViewModel::actualizarInfo,
                    onViewPassword = { inicioSesionViewModel.verClave(it)},
                    onIniciarSesionClicked = inicioSesionViewModel::validarCampos,
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
                    onViewPassword = {registroViewModel.verClave(it)},
                    onInicioSesionTextClicked = {navController.navigateUp()},
                    onRegistroClicked = registroViewModel::validarCampos,
                    onDialogClose =  registroViewModel::cerrarEmergente
                )
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