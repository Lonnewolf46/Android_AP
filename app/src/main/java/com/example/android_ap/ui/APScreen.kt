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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
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
import com.example.android_ap.ui.screens.ColaboradoresLayout
import com.example.android_ap.ui.screens.ForoLayout
import com.example.android_ap.ui.screens.GestionProyectosLayout
import com.example.android_ap.ui.screens.InformeGeneralLayout
import com.example.android_ap.ui.screens.InicioSesionLayout
import com.example.android_ap.ui.screens.MenuPrincipalLayout
import com.example.android_ap.ui.screens.ModificarInfoPersonalLayout
import com.example.android_ap.ui.screens.NotificacionesLayout
import com.example.android_ap.ui.screens.OpcionesLayout
import com.example.android_ap.ui.screens.ProyectoPlantillaLayout
import com.example.android_ap.ui.screens.RegistroLayout
import com.example.android_ap.ui.screens.ReunionLayout
import com.example.android_ap.ui.screens.TrabajoLayout

enum class APScreen() {
    InicioSesion,
    Registro,
    MenuPrincipal,
    Trabajo,
    Notificaciones,
    ModificarInfoPersonal,
    //Menu Principal > FAB//
    ForoGeneral,
    GestionProyectos,
    CrearProyecto,
    Colaboradores,
    //Trabajo > OpcionesProyecto
    OpcionesProyecto,
    ForoInterno,
    Reuniones,
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
    val reunionViewModel: ReunionViewModel = viewModel()
    val proyectoViewModel: ProyectoViewModel = viewModel()

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
        //Pantallas en las que NO deberia haber una barra superior
        APScreen.MenuPrincipal.name -> false
        APScreen.Trabajo.name -> false
        APScreen.Notificaciones.name -> false
        else -> true // en cualquier otro caso, mostrarla
    }

    showBottomBar = when (backStackEntry?.destination?.route) {
        //Pantallas en las que SI deberia haber una barra inferior
        APScreen.MenuPrincipal.name -> true
        APScreen.Trabajo.name -> true
        APScreen.Notificaciones.name -> true
        else -> false // en cualquier otro caso, ocultar
    }

    showFloatingButton = when (backStackEntry?.destination?.route) {
        //Pantallas en las que SI deberia haber un boton flotante
        APScreen.MenuPrincipal.name -> true
        APScreen.Trabajo.name -> true
        else -> false // en cualquier otro caso, mostrarla
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
                            path = { navController.navigate(APScreen.ForoGeneral.name) }),
                        MinFabItem(
                            icon = R.drawable.manage_project,
                            label = "Administrar proyectos",
                            path = { navController.navigate(APScreen.GestionProyectos.name) }),
                        MinFabItem(
                            icon = R.drawable.project_new,
                            label = "Agregar proyecto",
                            path = { proyectoViewModel.resetState()
                                navController.navigate(APScreen.CrearProyecto.name) }),
                        MinFabItem(
                            icon = R.drawable.addperson,
                            label = "Agregar colaborador",
                            path = { navController.navigate(APScreen.Colaboradores.name) })
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
        val reunionUiState by reunionViewModel.uiState.collectAsState()
        val proyectoUiState by proyectoViewModel.uiState.collectAsState()

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
                    nombre = inicioUiState.usuario,
                    clave = inicioUiState.clave,
                    passwordVisible = inicioUiState.claveVisible,
                    camposLlenos = inicioUiState.camposLlenos,
                    primerInicio = inicioUiState.primerInicio,
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

            //Foro General
            composable(route = APScreen.ForoGeneral.name){
                ForoLayout(
                    imagen = ImageVector.vectorResource(id = R.drawable.forums),
                    titulo = "Foro general",
                    onSendClick = { /*TODO*/ })
            }

            //GestionProyectos
            composable(route = APScreen.GestionProyectos.name){
                //DATOS MOCKUP
                GestionProyectosLayout(
                    onConsultar = {}
                )
            }

            //CrearProyecto
            composable(route = APScreen.CrearProyecto.name){
                ProyectoPlantillaLayout(
                    imagen = ImageVector.vectorResource(id = R.drawable.create_proj),
                    titulo = "Creación de proyecto",
                    nombre = proyectoUiState.nombre,
                    recursos = proyectoUiState.recursos,
                    presupuesto = proyectoUiState.presupuesto,
                    estado = proyectoUiState.estado,
                    descripcion = proyectoUiState.descripcion,
                    responsable = proyectoUiState.responsable,
                    onValueChange = proyectoViewModel::ActualizarCampos,
                    onAsignarColaboradores = { /*TODO*/ },
                    onCrearTareas = { /*TODO*/ },
                    onCrearProyecto = { /*TODO*/ },
                )
            }


            //Colaboradores
            composable(route = APScreen.Colaboradores.name){
                ColaboradoresLayout(
                    onAsignarClick = { /*TODO*/ },
                    onReasignarClick = { /*TODO*/ },
                    onEliminarClick = { /*TODO*/ }
                )
            }

            //OpcionesProyecto
            composable(route = APScreen.OpcionesProyecto.name){
                OpcionesLayout(
                    onForoClick = { navController.navigate(APScreen.ForoInterno.name) },
                    onReunionesClick = { navController.navigate(APScreen.Reuniones.name) },
                    onInformeClick = { navController.navigate(APScreen.InformeGeneral.name) },
                    onBurndownClick = { navController.navigate(APScreen.BurndownScreen.name) }
                )
            }

            //ForoInterno
            composable(route = APScreen.ForoInterno.name){
                ForoLayout(
                    imagen = ImageVector.vectorResource(id = R.drawable.forum_inside),
                    titulo = "Foro interno",
                    onSendClick = { /*TODO*/ })
            }

            //BurndownChart
            composable(route = APScreen.BurndownScreen.name){
                BurndownChartLayout()
            }

            //Informe general
            composable(route = APScreen.InformeGeneral.name){
                InformeGeneralLayout()
            }

            //Reunion
            composable(route = APScreen.Reuniones.name){
                ReunionLayout(
                    tema = reunionUiState.tema,
                    fecha = reunionUiState.fecha,
                    medio = reunionUiState.medio,
                    formato = reunionUiState.formato,
                    detalles = reunionUiState.detalles,
                    verAsignar = reunionUiState.verAsignar,
                    onAlternarAsignar = { reunionViewModel.VentanaAlternar() },
                    onValueChange = reunionViewModel::ActualizarCampos,
                    onAsignarColaboradores = { /*TODO*/ },
                    onCrearReunion = { /*TODO*/ }
                )
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

/*
* ¿Cómo hacer que recupere información antes de cargar algo? Simple, un cargar por funcion aparte, al
* estilo Login, que carga cosas y luego navega.
* Usar un try, para que si algo salga mal
* entonces navege a una pantalla plantilla que diga que algo salio mal.

¡¡¡¡¡Piensa en funciones!!!!!
 */