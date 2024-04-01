package com.example.android_ap.ui

import APIAccess
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android_ap.data.ReunionCampos
import com.example.android_ap.data.ReunionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

class ReunionViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ReunionUiState())
    val uiState: StateFlow<ReunionUiState> = _uiState.asStateFlow()

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

    /**
    Actualiza la informacion en uiState segun cada evento
     */
    fun ActualizarCampos(campo: ReunionCampos, texto: String){
        when(campo){
            ReunionCampos.TEMA -> {_uiState.update { currentState -> currentState.copy(tema = texto) } }
            ReunionCampos.FECHA -> {_uiState.update { currentState -> currentState.copy(fecha = texto) } }
            ReunionCampos.MEDIO -> {_uiState.update { currentState -> currentState.copy(medio = texto) } }
            ReunionCampos.FORMATO -> {_uiState.update { currentState -> currentState.copy(formato = texto) } }
            ReunionCampos.DETALLES -> {_uiState.update { currentState -> currentState.copy(detalles = texto) } }
        }
    }

    fun asignarQuitarcolaborador(idColaborador: Int){
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

    /**
    Valida que el uiState tenga información en todos sus campos. Si no es asi, avisa.
    CODIGOS:
    -1: Por defecto
    0: Proces correcto
    1: Quedan campos vacios
     */
    fun CrearReunion(){
        if(_uiState.value.tema.isNotBlank() &&
            _uiState.value.fecha.isNotBlank() &&
            _uiState.value.medio.isNotBlank() &&
            _uiState.value.formato.isNotBlank() &&
            _uiState.value.detalles.isNotBlank()
            ){
            //Validar que se hayan elegido colaboradores, de lo contrario no permite crear la reunion.
            if(_uiState.value.listaColaboradoresElegidos.isEmpty())
                _uiState.update { currentState -> currentState.copy(codigoResultado = 15)}
            else {
                //Intentar crear reunion


                //Codigo exito creacion
                _uiState.update { currentState -> currentState.copy(codigoResultado = 0) }
            }
        }
        else _uiState.update { currentState -> currentState.copy(codigoResultado = 1) }
    }

    fun InformacionPopupOff(){
        _uiState.update { currentState -> currentState.copy(codigoResultado = -1) }
    }

    fun VentanaAsignarAlternar(){
        _uiState.update { currentState -> currentState.copy(verAsignar =! _uiState.value.verAsignar) }
    }

    fun resetState(){
        _uiState.value = ReunionUiState()
    }
}