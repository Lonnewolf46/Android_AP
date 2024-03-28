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

    fun VentanaAlternar(){
        _uiState.update { currentState -> currentState.copy(verAsignar =! _uiState.value.verAsignar) }
    }

    fun resetState(){
        _uiState.value = ReunionUiState()
    }
}