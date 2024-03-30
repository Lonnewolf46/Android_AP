package com.example.android_ap.ui

import androidx.lifecycle.ViewModel
import com.example.android_ap.data.ReunionCampos
import com.example.android_ap.data.ReunionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReunionViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ReunionUiState())
    val uiState: StateFlow<ReunionUiState> = _uiState.asStateFlow()

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
            //Intentar crear reunion


            //Codigo exito creacion
            _uiState.update { currentState -> currentState.copy(codigoRespuesta = 0) }
        }
        else _uiState.update { currentState -> currentState.copy(codigoRespuesta = 1) }
    }

    fun InformacionPopupOff(){
        _uiState.update { currentState -> currentState.copy(codigoRespuesta = -1) }
    }

    fun VentanaAsignarAlternar(){
        _uiState.update { currentState -> currentState.copy(verAsignar =! _uiState.value.verAsignar) }
    }

    fun resetState(){
        _uiState.value = ReunionUiState()
    }
}