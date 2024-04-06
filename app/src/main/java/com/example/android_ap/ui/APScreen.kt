package com.example.android_ap.ui

import APIAccess
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
import com.example.android_ap.Estado
import com.example.android_ap.Notificacion
import com.example.android_ap.Proyecto
import com.example.android_ap.R
import com.example.android_ap.Response
import com.example.android_ap.data.RegistroCampos
import com.example.android_ap.data.UsuarioInfoCampos
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
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

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
    val foroViewModel: ForoViewModel = viewModel()
    val userInfoView: UserInfoView = viewModel()
    val colaboradoresViewModel: ColaboradoresViewModel = viewModel()

    //Inicializando elementos de navegacion
    val navController: NavHostController = rememberNavController()

    //Inicializando componentes de condiciones para Scaffolding
    var showTopBar by rememberSaveable { mutableStateOf(true) }
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    var showFloatingButton by rememberSaveable { mutableStateOf(true) }
    var fabState by rememberSaveable { mutableStateOf(FABState.COLLAPSED) }

    val inicioUiState by inicioSesionViewModel.uiState.collectAsState()
    val registroUiState by registroViewModel.uiState.collectAsState()
    val modInfoPersonalUiState by modInfoPersonalViewModel.uiState.collectAsState()
    val tareaUiState by tareaViewModel.uiState.collectAsState()
    val reunionUiState by reunionViewModel.uiState.collectAsState()
    val proyectoUiState by proyectoViewModel.uiState.collectAsState()
    val foroUiState by foroViewModel.uiState.collectAsState()
    val colaboradoresUiState by colaboradoresViewModel.uiState.collectAsState()
    val userInfo by userInfoView.uiState.collectAsState()

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
                        tareaViewModel.cargarTareasProyecto(userInfo.idProyecto)
                        navController.navigate(APScreen.Trabajo.name)
                    },
                    onAvisosClick = {
                        navController.popBackStack()
                        navController.navigate(APScreen.Notificaciones.name)
                    },
                    onMasClick = {
                        modInfoPersonalViewModel.cargarProyectos()
                        modInfoPersonalViewModel.cargarDepartamentos()
                        navController.navigate(APScreen.ModificarInfoPersonal.name) })
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
                            path = {
                                foroViewModel.cargarForoGeneral()
                                navController.navigate(APScreen.ForoGeneral.name)
                            }),
                        MinFabItem(
                            icon = R.drawable.manage_project,
                            label = "Administrar proyectos",
                            path = {
                                proyectoViewModel.cargarColaboradores(null)
                                navController.navigate(APScreen.GestionProyectos.name)
                            }),
                        MinFabItem(
                            icon = R.drawable.project_new,
                            label = "Agregar proyecto",
                            path = {
                                proyectoViewModel.resetState()
                                proyectoViewModel.cargarEstadosProyecto()
                                proyectoViewModel.cargarColaboradores(userInfo.id)
                                navController.navigate(APScreen.CrearProyecto.name) }),
                        MinFabItem(
                            icon = R.drawable.addperson,
                            label = "Agregar colaborador",
                            path = {
                                colaboradoresViewModel.cargarProyectos()
                                colaboradoresViewModel.cargarColaboradores()
                                colaboradoresViewModel.filtrarColaboradoresAdministradores()
                                navController.navigate(APScreen.Colaboradores.name)
                            })
                    )
                    //Crear boton flotante
                    FABMenuPrincipal(
                        buttonState = fabState,
                        onClick = {fabState = it },
                        items = items
                    )
                }

                //Si la pantalla activa es Trabajo
                if (backStackEntry?.destination?.route == APScreen.Trabajo.name){
                    //Crear botón flotante
                    FloatingActionButtonBasico(
                        onClick = {
                            tareaViewModel.cargarColaboradoresProyecto(userInfo.idProyecto)
                            tareaViewModel.cargarEstadosTarea()
                            tareaViewModel.hacerVisibleCrear()
                        })
                }
            }
        })
    { innerPadding ->

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
                    codigoResult = inicioUiState.codigoResultado,
                    onTextInput = inicioSesionViewModel::actualizarInfo,
                    onViewPassword = { inicioSesionViewModel.verClave(it) },
                    onIniciarSesionClicked = {
                        LoginToStart(
                            inicioSesionViewModel,
                            userInfoView,
                            modInfoPersonalViewModel,
                            navController
                        )
                    },
                    onRegistroTextClicked = {
                        registroViewModel.cargarDepartamentos()
                        registroViewModel.cargarProyectos()
                        navController.navigate(APScreen.Registro.name) },
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
                    proyecto = registroUiState.proyecto,
                    departamento = registroUiState.departamento,
                    listaProyectos = registroUiState.listaProyectos,
                    listaDepartamentos = registroUiState.listaDepartamentos,
                    clave = registroUiState.clave,
                    passwordVisible = registroUiState.claveVisible,
                    codigoRes = registroUiState.codigoResultado,
                    onTextInput = registroViewModel::actualizarDatos,
                    onProySelectionChange = registroViewModel::actualizarProy,
                    onDeptSelectionChange = { registroViewModel.actualizarDep(it) },
                    onViewPassword = { registroViewModel.verClave(it) },
                    onInicioSesionTextClicked = { navController.navigateUp() },
                    onRegistroClicked = registroViewModel::crearUsuario,
                    onRegistroExitoso = { navController.navigateUp() },
                    onDialogClose = registroViewModel::cerrarEmergente
                )
            }

            //Menu principal
            composable(route = APScreen.MenuPrincipal.name) {
                MenuPrincipalLayout(userInfo.proyecto)
            }

            //Modificacion de informacion personal
            composable(route = APScreen.ModificarInfoPersonal.name) {
                ModificarInfoPersonalLayout(
                    idUsuario = userInfo.id,
                    telefono = modInfoPersonalUiState.telefono,
                    email = modInfoPersonalUiState.correo,
                    proyecto = modInfoPersonalUiState.proyecto,
                    departamento = modInfoPersonalUiState.departamento,
                    listaProyectos = modInfoPersonalUiState.listaProyectos,
                    listaDepartamentos = modInfoPersonalUiState.listaDepartamentos,
                    codigoResult = modInfoPersonalUiState.codigoResultado,
                    onProySelectionChange = { modInfoPersonalViewModel.actualizarProy(it) },
                    onDeptoSelectionChange = { modInfoPersonalViewModel.actualizarDep(it) },
                    onTextInput = modInfoPersonalViewModel::actualizarDatos,
                    onActualizarClicked = { cambioProyecto(userInfoView, modInfoPersonalViewModel, userInfoView.uiState.value.id) },
                    onDialogClose = { modInfoPersonalViewModel.cerrarEmergente() }
                )
            }

            //Trabajo
            composable(route = APScreen.Trabajo.name) {
                TrabajoLayout(
                    idProyecto = userInfo.idProyecto,
                    nombreProyecto = userInfo.proyecto,
                    nombreTarea = tareaUiState.nombreTarea,
                    storyPoints = tareaUiState.storyPoints,
                    encargado = tareaUiState.encargado,
                    estado = tareaUiState.estado,
                    fechaFin = tareaUiState.fechaFin,
                    listaColaboradores = tareaUiState.listaColaboradores,
                    crearTareaVisible = tareaUiState.mostrar,
                    codigoResult = tareaUiState.codigoResultado,
                    listaTareas = tareaUiState.listaTareas,
                    listaEstados = tareaUiState.listaEstados,
                    onTareasColaborador = { tareaViewModel.cargarTareasColaborador(userInfo.id) },
                    onTareasProyecto = { tareaViewModel.cargarTareasProyecto(userInfo.idProyecto) },
                    onCerrarEmergente = { tareaViewModel.cerrarEmergente() },
                    onEditarTareaClick = tareaViewModel::cargarDatosTarea,
                    onOpcionesProyectoClick = { navController.navigate(APScreen.OpcionesProyecto.name) },
                    onTareaValueChange = tareaViewModel::ActualizarCampos,
                    onTareaEncargadoSelectionChange = tareaViewModel::ActualizarEncargado,
                    onTareaEstadoSelectionChange = tareaViewModel::ActualizarEstado,
                    onTareaConfirmar = {
                        if(tareaUiState.crearTarea) tareaViewModel.CrearTarea(userInfo.idProyecto)
                        else tareaViewModel.modificarTarea(tareaUiState.idTareaEditar,userInfo.idProyecto)
                                       },
                    onEliminarClick = { tareaViewModel.eliminarTarea(userInfo.idProyecto) },
                    onTareaCerrarClick = { tareaViewModel.cerrarCrearTarea()
                        if(!tareaUiState.crearTarea) tareaViewModel.vaciarTarea() },
                    crearTarea = tareaUiState.crearTarea)
            }

            //Notificaciones
            composable(route = APScreen.Notificaciones.name) {
                NotificacionesLayout( obtenerNotificaciones(userInfoView) )
            }

            //Foro General
            composable(route = APScreen.ForoGeneral.name){
                ForoLayout(
                    imagen = ImageVector.vectorResource(id = R.drawable.forums),
                    titulo = "Foro general",
                    texto = foroUiState.mensajeActual,
                    mensajes = foroUiState.mensajesForoGen,
                    codigoResult = foroUiState.codigoResultado,
                    onValueChange = foroViewModel::actualizarMensaje,
                    cerrarEmergente = { foroViewModel.cerrarEmergente() },
                    onSendClick = { foroViewModel.subirMensajeForoGeneral(userInfo.nombre, userInfo.id) })
            }

            //GestionProyectos
            composable(route = APScreen.GestionProyectos.name){
                GestionProyectosLayout(
                    listaProyectos = obtenerProyectos(),
                    listaColaboradores = proyectoUiState.listaColaboradores,
                    listaEstados = obtenerEstados(),
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
                    codigoResult = proyectoUiState.codigoResultado,
                    listaEstadosProyecto = proyectoUiState.listaEstadosProyecto,
                    listaEstadosTarea = proyectoUiState.listaEstadosTarea,
                    listaColaboradores = proyectoUiState.listaColaboradores,
                    listaColaboradoresElegidos = proyectoUiState.listaColaboradoresElegidos,
                    onValueChange = proyectoViewModel::actualizarCamposProyecto,
                    onEstadoProyectoSelection = { proyectoViewModel.actualizarEstadoProyecto(it) },
                    onResponsableProyectoSelection = { proyectoViewModel.actualizarResponsableProyecto(it) },
                    onAsignarColaboradores = { proyectoViewModel.ventanaAsignarColaboradores() },
                    onAgregarQuitarColaborador = { proyectoViewModel.asignarQuitarcolaborador(it) },
                    onCrearTareas = {
                        proyectoViewModel.cargarEstadosTarea()
                        proyectoViewModel.ventanaCrearTarea()
                                    },
                    onCrearProyecto = { proyectoViewModel.crearProyecto() },
                    onCerrarPopUp = {
                        if(proyectoUiState.codigoResultado == 0){
                            navController.navigateUp()
                            proyectoViewModel.resetState() }

                        else proyectoViewModel.CerrarEmergente()
                    },
                    nombreTarea = proyectoUiState.nombreTarea,
                    storyPointsTarea = proyectoUiState.storyPointsTarea,
                    fechaFinTarea = proyectoUiState.fechaFinTarea,
                    encargadoTarea = proyectoUiState.encargadoTarea,
                    estadoTarea = proyectoUiState.estadoTarea,
                    onCamposTareaValueChange = proyectoViewModel::actualizarCamposTarea,
                    onEncargadoTareaSelection = { proyectoViewModel.actualizarEncargadoTarea(it) },
                    onEstadoTareaSelection = { proyectoViewModel.actualizarEstadoTarea(it) },
                    onConfirmarAgregarTarea = { proyectoViewModel.crearTarea() },
                    codigoResultTarea = proyectoUiState.codigoResultadoTarea
                )
            }

            //Colaboradores
            composable(route = APScreen.Colaboradores.name){
                ColaboradoresLayout(
                    listaColaboradores = colaboradoresUiState.listaColaboradores.filter { it.id != userInfo.id },
                    listaProyectos = colaboradoresUiState.listaProyectos,
                    codigoPopUp = colaboradoresUiState.codigoPopUps,
                    codigoResult = colaboradoresUiState.codigoRespuesta,
                    onProyectoSelection = colaboradoresViewModel::actualizarProyecto,
                    onReasignarProyectoSelected = colaboradoresViewModel::actualizarIdColaborador,
                    onAlternarProyectoPopUp = { colaboradoresViewModel.alternarReasignarProyecto() },
                    onAlternarEliminarColaborador = { colaboradoresViewModel.alternarEliminarColaborador() },
                    onCerrarPopups = { colaboradoresViewModel.noEmergentes() }
                )
            }

            //OpcionesProyecto
            composable(route = APScreen.OpcionesProyecto.name){
                OpcionesLayout(
                    onForoClick = {
                        foroViewModel.cargarForoProyecto(userInfo.idProyecto)
                        navController.navigate(APScreen.ForoInterno.name)
                                  },
                    onReunionesClick = {
                        reunionViewModel.cargarColaboradoresProyecto(userInfo.idProyecto)
                        navController.navigate(APScreen.Reuniones.name)
                                       },
                    onInformeClick = { navController.navigate(APScreen.InformeGeneral.name) },
                    onBurndownClick = { navController.navigate(APScreen.BurndownScreen.name) }
                )
            }

            //ForoInterno
            composable(route = APScreen.ForoInterno.name){
                ForoLayout(
                    imagen = ImageVector.vectorResource(id = R.drawable.forum_inside),
                    titulo = "Foro interno: ${userInfo.proyecto}",
                    texto = foroUiState.mensajeActual,
                    mensajes = foroUiState.mensajesForoPro,
                    codigoResult = foroUiState.codigoResultado,
                    onValueChange = foroViewModel::actualizarMensaje,
                    cerrarEmergente = { foroViewModel.cerrarEmergente() },
                    onSendClick = { foroViewModel.subirMensajeForoProyecto(userInfo.nombre,userInfo.id, userInfo.idProyecto) })
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
                    codigoResult = reunionUiState.codigoResultado,
                    listaColaboradores = reunionUiState.listaColaboradores,
                    listaColaboradoresElegidos = reunionUiState.listaColaboradoresElegidos,
                    onAlternarAsignar = { reunionViewModel.VentanaAsignarAlternar() },
                    onInfoWindowClose = {
                        if(reunionUiState.codigoResultado == 0){
                            navController.navigateUp()
                            reunionViewModel.resetState()
                        }
                        else reunionViewModel.InformacionPopupOff()
                    },
                    onValueChange = reunionViewModel::ActualizarCampos,
                    onAsignarColaboradores = reunionViewModel::asignarQuitarcolaborador,
                    onCrearReunion = { reunionViewModel.CrearReunion(userInfo.id, userInfo.idProyecto) }
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
    userInfoView: UserInfoView,
    modInfoPersonalViewModel: ModificarInfoPersonalViewModel,
    navController: NavController
) {
    val output = inicioSesionViewModel.validarCampos()

    if(inicioSesionViewModel.uiState.value.loginExitoso && output!=null){
        userInfoView.actualizarInfo(UsuarioInfoCampos.IDENTIFICADOR, output.colaborador.id.toString())
        userInfoView.actualizarInfo(UsuarioInfoCampos.NOMBRE, output.colaborador.nombre)
        userInfoView.actualizarInfo(UsuarioInfoCampos.CORREO, output.colaborador.email)
        userInfoView.actualizarInfo(UsuarioInfoCampos.TELEFONO, output.colaborador.telefono.toString())
        userInfoView.actualizarInfo(UsuarioInfoCampos.DEPARTAMENTO, output.colaborador.departamento.nombre)
        userInfoView.actualizarInfo(UsuarioInfoCampos.IDPROYECTO, output.colaborador.proyecto.id.toString())
        userInfoView.actualizarInfo(UsuarioInfoCampos.PROYECTO, output.colaborador.proyecto.nombre)

        prepModInfoPersonalData(
            modInfoPersonalViewModel = modInfoPersonalViewModel,
            telefono = output.colaborador.telefono.toString(),
            proyecto = output.colaborador.proyecto.nombre,
            departamento = output.colaborador.departamento.nombre,
            email = output.colaborador.email

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
    telefono: String,
    proyecto: String,
    departamento: String,
    email: String
) {
    modInfoPersonalViewModel.actualizarDatos(RegistroCampos.TELEFONO, telefono)
    modInfoPersonalViewModel.actualizarDatos(RegistroCampos.PROYECTO, proyecto)
    modInfoPersonalViewModel.actualizarDatos(RegistroCampos.DEPARTAMENTO, departamento)
    modInfoPersonalViewModel.actualizarDatos(RegistroCampos.CORREO, email)
}

fun obtenerNotificaciones(currentUser: UserInfoView): List<Notificacion>{
    val apiAccess = APIAccess()
    return try {
        val resultado = runBlocking {
            apiAccess.putAPINotificaciones(currentUser.uiState.value.id)
        }
        resultado
    } catch (e: IOException) {
        listOf(Notificacion(1, "Error obteniendo las notificaciones", 1, 1))
    } catch (e: HttpException) {
        listOf(Notificacion(1, "Error obteniendo las notificaciones", 1, 1))
    }
}

fun cambioProyecto(userInfoView: UserInfoView,
                   modInfoPersonalViewModel: ModificarInfoPersonalViewModel,
                   idUsuario: Int,
){
    val valor = modInfoPersonalViewModel.subirCambios(idUsuario)
    if(valor != -1) {
        userInfoView.actualizarInfo(UsuarioInfoCampos.IDPROYECTO, valor.toString())
        userInfoView.actualizarInfo(UsuarioInfoCampos.PROYECTO, modInfoPersonalViewModel.uiState.value.proyecto)
    }
}

fun obtenerProyectos(): List<Proyecto> {
    val apiAccess = APIAccess()
    return try {
        val resultado = runBlocking {
            apiAccess.getAPIProyectos()
        }
        resultado.ifEmpty { emptyList() }
    }catch (e: IOException) {
        emptyList()
    } catch (e: HttpException) {
        emptyList()
    }
}

fun obtenerEstados(): List<Estado>{
    val apiAccess = APIAccess()
    return try {
        val resultado = runBlocking {
            apiAccess.getAPIProyectoEstados()
        }
        resultado.ifEmpty { emptyList() }
    }catch (e: IOException) {
        emptyList()
    } catch (e: HttpException) {
        emptyList()
    }
}

fun eliminarColaborador(idProyecto: Int, idColaborador: Int): Response{
    val apiAccess = APIAccess()
    return try {
        val resultado = runBlocking {
            apiAccess.deleteAPIEliminarColaborador(idProyecto, idColaborador)
        }
        resultado
    }catch (e: IOException) {
        Response(success = false)
    } catch (e: HttpException) {
        Response(success = false)
    }
}

/*
* ¿Cómo hacer que recupere información antes de cargar algo? Simple, un cargar por funcion aparte, al
* estilo Login, que carga cosas y luego navega.
* Usar un try, para que si algo salga mal
* entonces navege a una pantalla plantilla que diga que algo salio mal.

¡¡¡¡¡Piensa en funciones!!!!!
 */