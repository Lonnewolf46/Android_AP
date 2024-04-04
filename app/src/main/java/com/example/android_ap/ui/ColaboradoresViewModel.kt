package com.example.android_ap.ui

import APIAccess
import androidx.lifecycle.ViewModel
import com.example.android_ap.data.ColaboradoresUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

class ColaboradoresViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ColaboradoresUiState())
    val uiState: StateFlow<ColaboradoresUiState> = _uiState.asStateFlow()

    /**
     * Quita todos los colaboradores que clasifiquen como Responsables en algun proyecto
     */
    fun filtrarColaboradoresAdministradores(){
        if(_uiState.value.codigoRespuesta != 3 ){ //Si no hubo errores cargando las listas
            val idsAdministradores = _uiState.value.listaProyectos.map { it.idResponsable }
            val noAdministradores = _uiState.value.listaColaboradores.filter{
                colaborador -> colaborador.id !in idsAdministradores
            }

            _uiState.update { currentState -> currentState.copy(listaColaboradores = noAdministradores) }
        }
    }
    fun cargarColaboradores(){
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPIColaboradores()
            }
            _uiState.update { currentState -> currentState.copy(listaColaboradores = resultado) }
        }catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoRespuesta = 3) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoRespuesta = 3) }
        }
    }

    fun cargarProyectos(){
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPIProyectos()
            }
            _uiState.update { currentState -> currentState.copy(listaProyectos = resultado) }
        }catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoRespuesta = 3) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoRespuesta = -2) }
        }
    }

    fun actualizarIdColaborador(id: Int){
        _uiState.update { currentState -> currentState.copy(idColaborador = id) }
        }

    fun alternarReasignarProyecto(){
        if(_uiState.value.codigoPopUps == 1) //Si esta mostrandose, ocultar
            _uiState.update { currentState -> currentState.copy(codigoPopUps = -1) }
        else //Si esta oculto, mostrar
            _uiState.update { currentState -> currentState.copy(codigoPopUps = 1) }
    }

    fun alternarEliminarColaborador(){
        if(_uiState.value.codigoPopUps == 2) //Si esta mostrandose, ocultar
            _uiState.update { currentState -> currentState.copy(codigoPopUps = -1) }
        else //Si esta oculto, mostrar
            _uiState.update { currentState -> currentState.copy(codigoPopUps = 2) }
    }

    fun noEmergentes(){
        _uiState.update { currentState -> currentState.copy(codigoPopUps = -1) }
        _uiState.update { currentState -> currentState.copy(codigoRespuesta = -1) }
    }


    fun actualizarProyecto(idProyecto: Int){
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.putAPICambiarProyectoColaborador(
                    id = _uiState.value.idColaborador,
                    idProyecto = idProyecto)
            }
            if(resultado.success)
                _uiState.update { currentState -> currentState.copy(codigoRespuesta = 0) }
            else
                _uiState.update { currentState -> currentState.copy(codigoRespuesta = 3) }
        } catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoRespuesta = 3) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoRespuesta = -2) }
        }
    }
}