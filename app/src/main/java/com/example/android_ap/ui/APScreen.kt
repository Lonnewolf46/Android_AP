package com.example.android_ap.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android_ap.ui.screens.InicioSesionLayout

enum class APScreen(){
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
        backStackEntry?.destination?.route ?: APScreen.InicioSesion.name)

    Scaffold(topBar = {}) {
        innerPadding ->

        val InicioUiState by inicioSesionViewModel.uiState.collectAsState()
        val RegistroUiState by registroViewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = APScreen.InicioSesion.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ){
            //Inicio de Sesion
            composable(route = APScreen.InicioSesion.name){
                InicioSesionLayout(
                    InicioUiState.usuario,
                    InicioUiState.clave,
                    InicioUiState.oculta,
                    onTextInput = inicioSesionViewModel::actualizarInfo,
                    onViewPassword = {inicioSesionViewModel.verClave(it)}
                )
            }

            //Registro
            composable(route = APScreen.Registro.name){

            }
        }
    }

}