package com.example.android_ap.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.android_ap.data.RegistroCampos
import com.example.android_ap.data.RegistroUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegistroViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    private var projectExpanded by mutableStateOf(false)
    private var deptExpanded by mutableStateOf(false)

    fun actualizarDatos(campo: RegistroCampos, input: String){
        when(campo){
            RegistroCampos.NOMBRE -> {_uiState.update { currentState -> currentState.copy(nombre=input)}}
            RegistroCampos.CEDULA -> {_uiState.update { currentState -> currentState.copy(cedula=input)}}
            RegistroCampos.TELEFONO -> {_uiState.update { currentState -> currentState.copy(telefono=input)}}
            RegistroCampos.PROYECTO -> {_uiState.update { currentState -> currentState.copy(proyecto=input)}}
            RegistroCampos.DEPARTAMENTO -> {_uiState.update { currentState -> currentState.copy(departamento=input)}}
            RegistroCampos.EMAIL -> {_uiState.update { currentState -> currentState.copy(correo=input)}}
            RegistroCampos.CLAVE -> {_uiState.update { currentState -> currentState.copy(clave=input)}}
        }
    }

    fun verClave(estado: Boolean){
        _uiState.update { currentState -> currentState.copy(
            claveVisible = estado
        )}
    }

    fun validarCampos(){
        if (_uiState.value.nombre!="" &&
            _uiState.value.cedula!="" &&
            _uiState.value.telefono!="" &&
            _uiState.value.nombre!="" &&
            _uiState.value.correo!="" &&
            _uiState.value.clave!="")
        {
            //Actualizar valor para no mostrar aviso
            _uiState.update { currentState -> currentState.copy(camposLlenos = true)}

            //Hacer solicitud a la BD


        }
        else{
            _uiState.update { currentState -> currentState.copy(camposLlenos = false)}
        }
    }

    fun cerrarEmergente(){
        _uiState.update { currentState -> currentState.copy(camposLlenos = true)}
    }
}