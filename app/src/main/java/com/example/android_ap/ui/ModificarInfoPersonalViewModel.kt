package com.example.android_ap.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.android_ap.data.RegistroCampos
import com.example.android_ap.data.RegistroUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ModificarInfoPersonalViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    private var projectExpanded by mutableStateOf(false)
    private var deptExpanded by mutableStateOf(false)

    fun actualizarDatos(campo: RegistroCampos, input: String){
        when(campo){
            RegistroCampos.TELEFONO -> {_uiState.update { currentState -> currentState.copy(telefono=input)}}
            RegistroCampos.CORREO -> {_uiState.update { currentState -> currentState.copy(correo=input)}}
            RegistroCampos.PROYECTO -> {_uiState.update { currentState -> currentState.copy(proyecto=input)}}
            RegistroCampos.DEPARTAMENTO -> {_uiState.update { currentState -> currentState.copy(departamento=input)}}
            else -> {}
        }
    }

    fun validarCampos(){
        if (_uiState.value.telefono.isNotBlank() &&
            _uiState.value.correo.isNotBlank() &&
            _uiState.value.proyecto.isNotBlank() &&
            _uiState.value.departamento.isNotBlank()
            ){
            //Actualizar valor para no mostrar aviso
            _uiState.update { currentState -> currentState.copy(datosCorrectos = true)}

            //Hacer solicitud a la BD

        }
        else{
            _uiState.update { currentState -> currentState.copy(datosCorrectos = false)}
        }
    }

    fun cerrarEmergente(){
        _uiState.update { currentState -> currentState.copy(datosCorrectos = true)}
    }
}
