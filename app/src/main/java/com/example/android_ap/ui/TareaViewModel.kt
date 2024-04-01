package com.example.android_ap.ui

import APIAccess
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android_ap.data.TareaCampos
import com.example.android_ap.data.TareaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException
import java.util.Calendar

class TareaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TareaUiState())
    val uiState: StateFlow<TareaUiState> = _uiState.asStateFlow()

    fun cargarTareasProyecto(currentProyectId: Int) {
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPITareasProyecto(currentProyectId)
            }
            _uiState.update { currentState -> currentState.copy(listaTareas = resultado) }
        } catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaTareas = listOf()) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaTareas = listOf()) }
        }
    }

    fun cargarTareasColaborador(currentUserId: Int){
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPITareasColaborador(currentUserId)
            }
            _uiState.update { currentState -> currentState.copy(listaTareas = resultado) }
        } catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaTareas = listOf()) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaTareas = listOf()) }
        }
    }

    fun cargarColaboradoresProyecto(currentProyectId: Int){
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPIColaboradores(currentProyectId)
            }
            Log.d("RES", "Resultado: $resultado")
            _uiState.update { currentState -> currentState.copy(listaColaboradores = resultado) }
        } catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaColaboradores = listOf()) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaColaboradores = listOf()) }
        }
    }

    fun cargarEstadosTarea(){
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPIEstados()
            }
            Log.d("-----RES ESTADOS-----", "$resultado")
            _uiState.update { currentState -> currentState.copy(listaEstados = resultado) }
        } catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaEstados = listOf()) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaEstados = listOf()) }
        }
    }

    //Cargar datos en elementos de interfaz para luego desplegarla como moficacion
    fun cargarDatosTarea(idProyecto: Int,
                         nombre: String){
        val tarea = _uiState.value.listaTareas.firstOrNull { it.nombre == nombre }!!

        if(_uiState.value.listaColaboradores.isEmpty())
            this.cargarColaboradoresProyecto(idProyecto)
        if(_uiState.value.listaEstados.isEmpty())
            this.cargarEstadosTarea()

        val encargado: String = _uiState.value.listaColaboradores.firstOrNull{it.id == tarea.idEncargado}!!.nombre
        val estado: String = _uiState.value.listaEstados.firstOrNull{it.id == tarea.idEstado}!!.estado

        //2005-06-19T00:00:00.000Z to 2005-06-19
        val outputString = tarea.fechaFin.substring(0, 10)

        _uiState.update { currentState -> currentState.copy(idTareaEditar = tarea.id) }
        ActualizarCampos(TareaCampos.NOMBRE, tarea.nombre)
        ActualizarCampos(TareaCampos.STORYPOINTS, tarea.storyPoints.toString())
        ActualizarCampos(TareaCampos.ENCARGADO, encargado)
        ActualizarCampos(TareaCampos.ESTADO, estado)
        ActualizarCampos(TareaCampos.FECHAFIN, outputString)
        this.hacerVisibleEditar()
    }

    /**
    Actualiza la informacion en uiState segun cada evento
     */
    fun ActualizarCampos(campo: TareaCampos, input: String) {
        when (campo) {
            TareaCampos.NOMBRE -> {
                _uiState.update { currentState -> currentState.copy(nombreTarea = input) }
            }

            TareaCampos.STORYPOINTS -> {
                _uiState.update { currentState -> currentState.copy(storyPoints = input) }
            }

            TareaCampos.ENCARGADO -> {
                _uiState.update { currentState -> currentState.copy(encargado = input) }
            }

            TareaCampos.ESTADO -> {
                _uiState.update { currentState -> currentState.copy(estado = input) }
            }

            TareaCampos.FECHAFIN -> {
                _uiState.update { currentState -> currentState.copy(fechaFin = input) }
            }
        }
    }

    fun ActualizarEncargado(input: String) {
        ActualizarCampos(TareaCampos.ENCARGADO, input)
    }

    fun ActualizarEstado(input: String){
        ActualizarCampos(TareaCampos.ESTADO, input)
    }

    fun cerrarEmergente() {
        _uiState.update { currentState -> currentState.copy(codigoResultado = -1) }
    }

    fun cerrarCrearTarea(){
        _uiState.update { currentState -> currentState.copy(mostrar = false) }
    }

    /**
    Funcion para crear una tarea
    CODIGOS
    -1: Por defecto
    0: Correcto
    8: Nombre de tarea incorrecto
    9: StoryPoints incorrecto
    10: Encargado no seleccionado
    11: Estado no seleccionado
    12: Fecha de fin tarea no seleccionada
     */
    fun CrearTarea(idProyecto: Int) {
        if (_uiState.value.nombreTarea.isBlank()) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 8) }
        } else if (_uiState.value.storyPoints.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultado = 9) }
        else if (_uiState.value.encargado.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultado = 10) }
        else if(_uiState.value.estado.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultado = 11) }
        else if(_uiState.value.fechaFin.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultado = 12) }
        else {

            //Aqui van acciones para crear tarea nueva
            val apiAccess = APIAccess()
            try {
                val idColaborador = _uiState.value.listaColaboradores.firstOrNull { it.nombre == _uiState.value.encargado }!!.id
                val idEstado = _uiState.value.listaEstados.firstOrNull { it.estado == _uiState.value.estado }!!.id
                // inicializando calendario
                val mCalendar = Calendar.getInstance()

                // Recuperando fecha, mes y dia actual
                val mYear = mCalendar.get(Calendar.YEAR)
                val mMonth = mCalendar.get(Calendar.MONTH) + 1
                val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

                val resultado = runBlocking {
                    apiAccess.postAPINuevaTarea(
                        idProyecto = idProyecto,
                        nombre = _uiState.value.nombreTarea,
                        storyPoints = _uiState.value.storyPoints,
                        idEncargado = idColaborador,
                        fechaInicio = "$mYear-${mMonth}-$mDay",
                        fechaFin = _uiState.value.fechaFin,
                        idEstado = idEstado
                    )

                }
                if (resultado.success){
                    this.cerrarCrearTarea()
                    this.vaciarTarea()
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 0) }
                    this.cargarTareasProyecto(idProyecto)
                }
                else{
                    this.cerrarCrearTarea()
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
                    }
            }catch (e: IOException) {
                this.cerrarCrearTarea()
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            } catch (e: HttpException) {
                this.cerrarCrearTarea()
                _uiState.update { currentState -> currentState.copy(codigoResultado = -2) }
            }
        }
    }

    fun modificarTarea(idTarea: Int, idProyecto: Int){
        if (_uiState.value.nombreTarea.isBlank()) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 8) }
        } else if (_uiState.value.storyPoints.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultado = 9) }
        else if (_uiState.value.encargado.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultado = 10) }
        else if(_uiState.value.estado.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultado = 11) }
        else if(_uiState.value.fechaFin.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultado = 12) }
        else {

            //Aqui van acciones para crear tarea nueva
            val apiAccess = APIAccess()
            try {
                val idColaborador = _uiState.value.listaColaboradores.firstOrNull { it.nombre == _uiState.value.encargado }!!.id
                val idEstado = _uiState.value.listaEstados.firstOrNull { it.estado == _uiState.value.estado }!!.id

                val resultado = runBlocking {
                    apiAccess.putAPIModificarTarea(
                        id = idTarea,
                        nombre = _uiState.value.nombreTarea,
                        storyPoints = _uiState.value.storyPoints,
                        idEncargado = idColaborador,
                        fechaFin = _uiState.value.fechaFin,
                        idEstado = idEstado
                    )
                }
                if (resultado.success){
                    this.cerrarCrearTarea()
                    _uiState.update { currentState -> currentState.copy(crearTarea = true) }
                    this.vaciarTarea()
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 13) }
                }
                else{
                    this.cerrarCrearTarea()
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
                }
            }catch (e: IOException) {
                this.cerrarCrearTarea()
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            } catch (e: HttpException) {
                this.cerrarCrearTarea()
                _uiState.update { currentState -> currentState.copy(codigoResultado = -2) }
            }
        }
    }

    fun vaciarTarea(){
        _uiState.update { currentState -> currentState.copy(
            nombreTarea = "",
            storyPoints = "",
            encargado = "",
            fechaFin = "",
            estado = "")
        }
    }

    fun hacerVisibleCrear() {
        _uiState.update { currentState -> currentState.copy(crearTarea = true) }
        _uiState.update { currentState -> currentState.copy(mostrar = true) }
    }

    fun hacerVisibleEditar(){
        _uiState.update { currentState -> currentState.copy(crearTarea = false) }
        _uiState.update { currentState -> currentState.copy(mostrar = true) }
    }

    fun resetState() {
        _uiState.value = TareaUiState()
    }
}
