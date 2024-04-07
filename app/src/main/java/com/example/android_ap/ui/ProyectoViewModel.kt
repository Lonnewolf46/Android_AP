package com.example.android_ap.ui

import APIAccess
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android_ap.Proyecto
import com.example.android_ap.ProyectoEnviar
import com.example.android_ap.Tarea
import com.example.android_ap.data.ProyectoCampos
import com.example.android_ap.data.ProyectoUiState
import com.example.android_ap.data.TareaCampos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException
import java.util.Calendar

class ProyectoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProyectoUiState())
    val uiState: StateFlow<ProyectoUiState> = _uiState.asStateFlow()

    fun cargarEstadosProyecto() {
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPIEstados()
            }
            _uiState.update { currentState -> currentState.copy(listaEstadosProyecto = resultado) }
        } catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaEstadosProyecto = listOf()) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaEstadosProyecto = listOf()) }
        }
    }

    fun cargarEstadosTarea() {
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPIEstados()
            }
            _uiState.update { currentState -> currentState.copy(listaEstadosTarea = resultado) }
        } catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaEstadosTarea = listOf()) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaEstadosTarea = listOf()) }
        }
    }

    fun cargarColaboradores(idActual: Int?) {
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPIColaboradores()
            }
            if (idActual != null) //Si se dio un id para filtrarlo fuera, se filtra
                _uiState.update { currentState -> currentState.copy(listaColaboradores = resultado.filter { it.id != idActual }) }
            else //Si no, se conserva
                _uiState.update { currentState -> currentState.copy(listaColaboradores = resultado) }
        } catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        }
    }

    fun cargarColaboradoresProyecto(currentProyectId: Int) {
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPIColaboradores(currentProyectId)
            }
            Log.d("COLABORADORES DEL PROYECTO CARGADOS: ", "$resultado")
            _uiState.update { currentState -> currentState.copy(listaColaboradores = resultado) }
            _uiState.update { currentState -> currentState.copy(listaColaboradoresElegidos = resultado.map { it.id }) }
        } catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaColaboradores = listOf()) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            _uiState.update { currentState -> currentState.copy(listaColaboradores = listOf()) }
        }
    }

    fun asignarQuitarcolaborador(idColaborador: Int) {
        val list = _uiState.value.listaColaboradoresElegidos

        //Si el colaborador ya fue elegido entonces lo quita
        if (list.contains(element = idColaborador)) {
            _uiState.update { currentState ->
                currentState.copy(
                    listaColaboradoresElegidos =
                    _uiState.value.listaColaboradoresElegidos.toMutableList()
                        .apply { remove(idColaborador) }
                )
            }
        }
        //Si el colaborador no ha sido elegido entonces lo agrega
        else {
            _uiState.update { currentState ->
                currentState.copy(
                    listaColaboradoresElegidos =
                    _uiState.value.listaColaboradoresElegidos.toMutableList()
                        .apply { add(idColaborador) }
                )
            }
        }
    }

    fun actualizarCamposTarea(campo: TareaCampos, input: String) {
        when (campo) {
            TareaCampos.NOMBRE -> {
                _uiState.update { currentState -> currentState.copy(nombreTarea = input) }
            }

            TareaCampos.STORYPOINTS -> {
                _uiState.update { currentState -> currentState.copy(storyPointsTarea = input) }
            }

            TareaCampos.ENCARGADO -> {
                _uiState.update { currentState -> currentState.copy(encargadoTarea = input) }
            }

            TareaCampos.ESTADO -> {
                _uiState.update { currentState -> currentState.copy(estadoTarea = input) }
            }

            TareaCampos.FECHAFIN -> {
                _uiState.update { currentState -> currentState.copy(fechaFinTarea = input) }
            }
        }
    }

    fun actualizarEstadoTarea(input: String) {
        this.actualizarCamposTarea(TareaCampos.ESTADO, input)
    }

    fun actualizarEncargadoTarea(input: String) {
        this.actualizarCamposTarea(TareaCampos.ENCARGADO, input)
    }

    /*
    8 nombre vacio
    9 tarea vacio
    10 encargado vacio
    11 estado vacio
    12 fecha fin vacio
    * */
    fun crearTarea() {
        if (_uiState.value.nombreTarea.isBlank()) {
            _uiState.update { currentState -> currentState.copy(codigoResultadoTarea = 8) }
        } else if (_uiState.value.storyPointsTarea.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultadoTarea = 9) }
        else if (_uiState.value.encargadoTarea.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultadoTarea = 10) }
        else if (_uiState.value.estadoTarea.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultadoTarea = 11) }
        else if (_uiState.value.fechaFinTarea.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultadoTarea = 12) }
        else {

            val idColaborador =
                _uiState.value.listaColaboradores.firstOrNull { it.nombre == _uiState.value.encargadoTarea }!!.id
            val idEstado =
                _uiState.value.listaEstadosTarea.firstOrNull { it.estado == _uiState.value.estadoTarea }!!.id

            // inicializando calendario
            val mCalendar = Calendar.getInstance()
            // Recuperando fecha, mes y dia actual
            val mYear = mCalendar.get(Calendar.YEAR)
            val mMonth = mCalendar.get(Calendar.MONTH) + 1
            val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

            val nuevaTarea = Tarea(
                id = -1,
                nombre = _uiState.value.nombreTarea,
                storyPoints = _uiState.value.storyPointsTarea.toInt(),
                idProyecto = -1,
                idEncargado = idColaborador,
                fechaInicio = "$mYear-${mMonth}-$mDay",
                fechaFin = _uiState.value.fechaFinTarea,
                idEstado = idEstado
            )
            _uiState.update { currentState ->
                currentState.copy(
                    listaTareas =
                    _uiState.value.listaTareas.toMutableList()
                        .apply { add(nuevaTarea) }
                )
            }
            //Codigos de exito
            _uiState.update { currentState -> currentState.copy(codigoResultado = 18) }
            _uiState.update { currentState -> currentState.copy(codigoResultadoTarea = -1) }

            //Limpiar elementos de interfaz
            _uiState.update { currentState -> currentState.copy(nombreTarea = "") }
            _uiState.update { currentState -> currentState.copy(storyPointsTarea = "") }
            _uiState.update { currentState -> currentState.copy(encargadoTarea = "") }
            _uiState.update { currentState -> currentState.copy(estadoTarea = "") }
            _uiState.update { currentState -> currentState.copy(fechaFinTarea = "") }
        }
    }

    fun actualizarCamposProyecto(campo: ProyectoCampos, texto: String) {
        when (campo) {
            ProyectoCampos.NOMBRE -> {
                _uiState.update { currentState -> currentState.copy(nombre = texto) }
            }

            ProyectoCampos.RECURSOS -> {
                _uiState.update { currentState -> currentState.copy(recursos = texto) }
            }

            ProyectoCampos.PRESUPUESTO -> {
                _uiState.update { currentState -> currentState.copy(presupuesto = texto) }
            }

            ProyectoCampos.ESTADO -> {
                _uiState.update { currentState -> currentState.copy(estado = texto) }
            }

            ProyectoCampos.DESCRIPCION -> {
                _uiState.update { currentState -> currentState.copy(descripcion = texto) }
            }

            ProyectoCampos.RESPONSABLE -> {
                _uiState.update { currentState -> currentState.copy(responsable = texto) }
            }
        }
    }

    fun actualizarEstadoProyecto(input: String) {
        this.actualizarCamposProyecto(ProyectoCampos.ESTADO, input)
    }

    fun actualizarResponsableProyecto(input: String) {
        this.actualizarCamposProyecto(ProyectoCampos.RESPONSABLE, input)
    }

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

    fun cargarDatosProyecto(proyectoElegido: Proyecto) {

        this.cargarColaboradores(null)
        this.cargarEstadosProyecto()
        this.cargarTareasProyecto(proyectoElegido.id)

        if (_uiState.value.listaColaboradores.isEmpty() || _uiState.value.listaEstadosProyecto.isEmpty())
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        else {
            val nombreColaborador =
                _uiState.value.listaColaboradores.firstOrNull { it.id == proyectoElegido.idResponsable }!!.nombre
            val nombreEstado =
                _uiState.value.listaEstadosProyecto.firstOrNull { it.id == proyectoElegido.idEstado }!!.estado
            this.cargarColaboradoresProyecto(proyectoElegido.id)
            _uiState.update { currentState ->
                currentState.copy(
                    idProyecto = proyectoElegido.id,
                    nombre = proyectoElegido.nombre,
                    recursos = proyectoElegido.recursos,
                    presupuesto = proyectoElegido.presupuesto.toString(),
                    estado = nombreEstado,
                    descripcion = proyectoElegido.descripcion,
                    responsable = nombreColaborador,
                    crearProyecto = false
                )
            }
        }
    }

    /**
    Valida que el uiState tenga información en todos sus campos. Si no es asi, avisa.
    CODIGOS:
    -1: Por defecto
    0: Proceso correcto
    1: Quedan campos vacios
     */
    fun crearProyecto() {
        if (_uiState.value.nombre.isNotBlank() &&
            _uiState.value.recursos.isNotBlank() &&
            _uiState.value.presupuesto.isNotBlank() &&
            _uiState.value.estado.isNotBlank() &&
            _uiState.value.descripcion.isNotBlank() &&
            _uiState.value.responsable.isNotBlank()
        ) {
            if (_uiState.value.listaColaboradoresElegidos.isEmpty())//Si no hay colaboradores
                _uiState.update { currentState -> currentState.copy(codigoResultado = 16) }
            else if (_uiState.value.listaTareas.isEmpty())//Si no hay tareas
                _uiState.update { currentState -> currentState.copy(codigoResultado = 19) }
            else {

                val idColaborador =
                    _uiState.value.listaColaboradores.firstOrNull { it.nombre == _uiState.value.responsable }!!.id
                val idEstado =
                    _uiState.value.listaEstadosProyecto.firstOrNull { it.estado == _uiState.value.estado }!!.id

                //Hacer solicitud
                val proyecto = ProyectoEnviar(
                    id = -1,
                    nombre = _uiState.value.nombre,
                    recursos = _uiState.value.recursos,
                    presupuesto = _uiState.value.presupuesto.toFloat(),
                    idEstado = idEstado,
                    descripcion = _uiState.value.descripcion,
                    idResponsable = idColaborador,
                    fechaInicio = _uiState.value.listaTareas[0].fechaInicio,
                    fechaFin = _uiState.value.listaTareas[0].fechaFin,
                    tareas = _uiState.value.listaTareas,
                    colaboradores = _uiState.value.listaColaboradoresElegidos
                )

                val apiAccess = APIAccess()
                try {
                    val resultado = runBlocking {
                        apiAccess.postAPICrearProyecto(proyecto)
                    }
                    if (resultado.success)
                        _uiState.update { currentState -> currentState.copy(codigoResultado = 0) }
                    else
                        _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
                } catch (e: IOException) {
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
                } catch (e: HttpException) {
                    _uiState.update { currentState -> currentState.copy(codigoResultado = -2) }
                }
            }
        } else _uiState.update { currentState -> currentState.copy(codigoResultado = 1) }
    }

    fun modificarProyecto() {
        if (_uiState.value.nombre.isNotBlank() &&
            _uiState.value.recursos.isNotBlank() &&
            _uiState.value.presupuesto.isNotBlank() &&
            _uiState.value.estado.isNotBlank() &&
            _uiState.value.descripcion.isNotBlank() &&
            _uiState.value.responsable.isNotBlank()
        ) {
            val idColaborador =
                _uiState.value.listaColaboradores.firstOrNull { it.nombre == _uiState.value.responsable }!!.id
            val idEstado =
                _uiState.value.listaEstadosProyecto.firstOrNull { it.estado == _uiState.value.estado }!!.id

            //Hacer solicitud
            val proyecto = ProyectoEnviar(
                id = _uiState.value.idProyecto,
                nombre = _uiState.value.nombre,
                recursos = _uiState.value.recursos,
                presupuesto = _uiState.value.presupuesto.toFloat(),
                idEstado = idEstado,
                descripcion = _uiState.value.descripcion,
                idResponsable = idColaborador,
                fechaInicio = _uiState.value.listaTareas[0].fechaInicio,
                fechaFin = _uiState.value.listaTareas[0].fechaFin,
                tareas = _uiState.value.listaTareas,
                colaboradores = _uiState.value.listaColaboradoresElegidos
            )

            val apiAccess = APIAccess()
            try {
                val resultado = runBlocking {
                    apiAccess.putAPIModificarProyecto(_uiState.value.idProyecto,proyecto)
                }
                if (resultado.success)
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 0) }
                else
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            } catch (e: IOException) {
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            } catch (e: HttpException) {
                _uiState.update { currentState -> currentState.copy(codigoResultado = -2) }
            }
        } else _uiState.update { currentState -> currentState.copy(codigoResultado = 1) }
    }

    fun ventanaAsignarColaboradores() {//Abrir ventana asignar colaboradores
        _uiState.update { currentState -> currentState.copy(codigoResultado = 16) }
    }

    fun ventanaCrearTarea() {//Abrir ventana Crear Tarea
        _uiState.update { currentState -> currentState.copy(codigoResultado = 17) }
    }

    fun ventanaVerColaboradores() {
        _uiState.update { currentState -> currentState.copy(codigoResultado = 20) }
    }

    fun ventanaVerTareas() {
        _uiState.update { currentState -> currentState.copy(codigoResultado = 21) }
    }

    fun boolCrearProyecto() {
        _uiState.update { currentState -> currentState.copy(crearProyecto = true) }
    }

    fun CerrarEmergente() {
        _uiState.update { currentState -> currentState.copy(codigoResultado = -1) }
    }

    fun resetState() {
        _uiState.value = ProyectoUiState()
    }
}