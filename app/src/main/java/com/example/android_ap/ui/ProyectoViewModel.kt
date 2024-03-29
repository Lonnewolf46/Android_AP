package com.example.android_ap.ui

import androidx.lifecycle.ViewModel
import com.example.android_ap.data.ProyectoCampos
import com.example.android_ap.data.ProyectoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProyectoViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ProyectoUiState())
    val uiState: StateFlow<ProyectoUiState> = _uiState.asStateFlow()

    fun ActualizarCampos(campo: ProyectoCampos, texto: String){
        when(campo){
            ProyectoCampos.NOMBRE -> {_uiState.update { currentState -> currentState.copy(nombre = texto) } }
            ProyectoCampos.RECURSOS -> {_uiState.update { currentState -> currentState.copy(recursos = texto) } }
            ProyectoCampos.PRESUPUESTO -> {_uiState.update { currentState -> currentState.copy(presupuesto = texto) } }
            ProyectoCampos.ESTADO -> {_uiState.update { currentState -> currentState.copy(estado = texto) } }
            ProyectoCampos.DESCRIPCION -> {_uiState.update { currentState -> currentState.copy(descripcion = texto) } }
            ProyectoCampos.RESPONSABLE -> {_uiState.update { currentState -> currentState.copy(responsable = texto) } }
        }
    }

    fun resetState(){
        _uiState.value = ProyectoUiState()
    }
}