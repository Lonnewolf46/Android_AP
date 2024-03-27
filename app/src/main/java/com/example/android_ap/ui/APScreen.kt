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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android_ap.R
import com.example.android_ap.data.RegistroCampos
import com.example.android_ap.ui.UIAuxiliar.APAppBar
import com.example.android_ap.ui.UIAuxiliar.BottomAppBarMenu
import com.example.android_ap.ui.UIAuxiliar.FABMenuPrincipal
import com.example.android_ap.ui.UIAuxiliar.FABState
import com.example.android_ap.ui.UIAuxiliar.FloatingActionButtonBasico
import com.example.android_ap.ui.UIAuxiliar.MinFabItem
import com.example.android_ap.ui.screens.BurndownChartLayout
import com.example.android_ap.ui.screens.InformeGeneralLayout
import com.example.android_ap.ui.screens.InicioSesionLayout
import com.example.android_ap.ui.screens.MenuPrincipalLayout
import com.example.android_ap.ui.screens.ModificarInfoPersonalLayout
import com.example.android_ap.ui.screens.NotificacionesLayout
import com.example.android_ap.ui.screens.OpcionesLayout
import com.example.android_ap.ui.screens.RegistroLayout
import com.example.android_ap.ui.screens.TrabajoLayout

enum class APScreen() {
    InicioSesion,
    Registro,
    MenuPrincipal,
    Trabajo,
    Notificaciones,
    ModificarInfoPersonal,
    OpcionesProyecto,
    InformeGeneral,
    BurndownScreen
}

@Composable
fun AP_App() {

    //ViewModels
    val inicioSesionViewModel: InicioSesionViewModel = viewModel()
    val registroViewModel: RegistroViewModel = viewModel()
    val modInfoPersonalViewModel: ModificarInfoPersonalViewModel = viewModel()
    val tareaViewModel: TareaViewModel = viewModel()

    //Inicializando elementos de navegacion
    val navController: NavHostController = rememberNavController()

    //Inicializando componentes de condiciones para Scaffolding
    var showTopBar by rememberSaveable { mutableStateOf(true) }
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    var showFloatingButton by rememberSaveable { mutableStateOf(true) }
    var fabState by rememberSaveable { mutableStateOf(FABState.COLLAPSED) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = APScreen.valueOf(
        backStackEntry?.destination?.route ?: APScreen.InicioSesion.name
    )

    showTopBar = when (backStackEntry?.destination?.route) {
        //Pantallas en las que no deberia haber una barra superior
        APScreen.MenuPrincipal.name -> false
        APScreen.Trabajo.name -> false
        APScreen.Notificaciones.name -> false
        else -> true // en cualquier otro caso, mostrarla
    }

    showBottomBar = when (backStackEntry?.destination?.route) {
        //Pantallas en las que no deberia haber una barra inferior
        APScreen.InicioSesion.name -> false
        APScreen.Registro.name -> false
        APScreen.ModificarInfoPersonal.name -> false
        APScreen.OpcionesProyecto.name -> false
        APScreen.InformeGeneral.name -> false
        APScreen.BurndownScreen.name -> false
        else -> true // en cualquier otro caso, mostrarla
    }

    showFloatingButton = when (backStackEntry?.destination?.route) {
        //Pantallas en las que no deberia haber una barra inferior
        APScreen.InicioSesion.name -> false
        APScreen.Registro.name -> false
        APScreen.Notificaciones.name -> false
        APScreen.ModificarInfoPersonal.name -> false
        APScreen.OpcionesProyecto.name -> false
        APScreen.InformeGeneral.name -> false
        APScreen.BurndownScreen.name -> false
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
            if (showBottomBar) {
                BottomAppBarMenu(
                    onInicioClick = {
                        navController.popBackStack()
                        navController.navigate(APScreen.MenuPrincipal.name)
                    },
                    onTrabajoClick = {
                        navController.popBackStack()
                        navController.navigate(APScreen.Trabajo.name)
                    },
                    onAvisosClick = {
                        navController.popBackStack()
                        navController.navigate(APScreen.Notificaciones.name)
                    },
                    onMasClick = { navController.navigate(APScreen.ModificarInfoPersonal.name) })
            }
        },

        floatingActionButton = {
            if (showBottomBar) {
                //Si la pantalla activa es Menu Principal
                if (backStackEntry?.destination?.route == APScreen.MenuPrincipal.name) {
                    //Crear opciones desplegadas del boton flotante
                    val items = listOf(
                        MinFabItem(
                            icon = R.drawable.forum_icon,
                            label = "Foro General",
                            path = {/*TODO*/ }),
                        MinFabItem(
                            icon = R.drawable.manage_project,
                            label = "Administrar proyectos",
                            path = {/*TODO*/ }),
                        MinFabItem(
                            icon = R.drawable.project_new,
                            label = "Agregar proyecto",
                            path = {/*TODO*/ }),
                        MinFabItem(
                            icon = R.drawable.addperson,
                            label = "Agregar colaborador",
                            path = {/*TODO*/ })
                    )
                    //Crear boton flotante
                    FABMenuPrincipal(
                        buttonState = fabState,
                        onClick = { fabState = it },
                        items = items
                    )
                }

                //Si la pantalla activa es Trabajo
                if (backStackEntry?.destination?.route == APScreen.Trabajo.name){
                    //Crear botón flotante
                    FloatingActionButtonBasico(onClick = tareaViewModel::HacerVisible)
                }
            }
        })
    { innerPadding ->

        val inicioUiState by inicioSesionViewModel.uiState.collectAsState()
        val registroUiState by registroViewModel.uiState.collectAsState()
        val modInfoPersonalUiState by modInfoPersonalViewModel.uiState.collectAsState()
        val tareaUiState by tareaViewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = APScreen.InicioSesion.name, //PUNTO DE ENTRADA
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
            composable(route = APScreen.ModificarInfoPersonal.name) {
                ModificarInfoPersonalLayout(
                    telefono = modInfoPersonalUiState.telefono,
                    email = modInfoPersonalUiState.correo,
                    camposLlenos = modInfoPersonalUiState.camposLlenos,
                    onTextInput = modInfoPersonalViewModel::actualizarDatos,
                    onActualizarClicked = { /*TODO*/ }) {
                }
            }

            //Trabajo
            composable(route = APScreen.Trabajo.name) {
                TrabajoLayout(
                    nombre = tareaUiState.nombreTarea,
                    storyPoints = tareaUiState.storyPoints,
                    encargado = tareaUiState.encargado,
                    crearTareaVisible = tareaUiState.mostrar,
                    onOpcionesProyectoClick = { navController.navigate(APScreen.OpcionesProyecto.name) },
                    onCrearTareaValueChange = tareaViewModel::ActualizarCampos,
                    onCrearTareaConfirmar = { tareaViewModel.CrearTarea() },
                    onCrearTareaCerrarClick = { tareaViewModel.resetState() })
            }

            //Notificaciones
            composable(route = APScreen.Notificaciones.name) {
                NotificacionesLayout()
            }

            //OpcionesProyecto
            composable(route = APScreen.OpcionesProyecto.name){
                OpcionesLayout(
                    onForoClick = {},
                    onReunionesClick = {},
                    onInformeClick = { navController.navigate(APScreen.InformeGeneral.name) },
                    onBurndownClick = { navController.navigate(APScreen.BurndownScreen.name) }
                )
            }

            //BurndownChart
            composable(route = APScreen.BurndownScreen.name){
                BurndownChartLayout()
            }

            //Informe general
            composable(route = APScreen.InformeGeneral.name){
                InformeGeneralLayout()
            }
        }
    }
}

/**
Proceso de inicio de sesion
 */
private fun LoginToStart(
    inicioSesionViewModel: InicioSesionViewModel,
    modInfoPersonalViewModel: ModificarInfoPersonalViewModel,
    navController: NavController
) {
    inicioSesionViewModel.validarCampos()

    if (inicioSesionViewModel.uiState.value.camposLlenos) {

        //Llenar currentUser con valores del la BD o algo aquí
        /*TODO*/

        prepModInfoPersonalData(
            modInfoPersonalViewModel,
            inicioSesionViewModel.uiState.value.usuario
        )
        inicioSesionViewModel.resetState()
        navController.popBackStack()
        navController.navigate(APScreen.MenuPrincipal.name)
    }
}

/**
 * Alterar el estado del viewModel para preservar la información
 */
private fun prepModInfoPersonalData(
    modInfoPersonalViewModel: ModificarInfoPersonalViewModel,
    email: String
) {

    //Datos mockup
    modInfoPersonalViewModel.actualizarDatos(RegistroCampos.TELEFONO, "83035422")
    modInfoPersonalViewModel.actualizarDatos(RegistroCampos.PROYECTO, "Proyecto 1")
    modInfoPersonalViewModel.actualizarDatos(RegistroCampos.DEPARTAMENTO, "Departamento 1")
    modInfoPersonalViewModel.actualizarDatos(RegistroCampos.CORREO, email)
}