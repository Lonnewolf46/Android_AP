package com.example.android_ap.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.android_ap.data.RegistroUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegistroViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    private var estadoSaved by mutableStateOf(false)

    fun actualizarDatos(campo: Campo, input: String){
        when(campo){
            Campo.NOMBRE -> {_uiState.update { currentState -> currentState.copy(nombre=input)}}
            Campo.CEDULA -> {_uiState.update { currentState -> currentState.copy(cedula=input)}}
            Campo.TELEFONO -> {_uiState.update { currentState -> currentState.copy(nombre=input)}}
            Campo.PROYECTO -> {_uiState.update { currentState -> currentState.copy(proyecto=input)}}
            Campo.DEPARTAMENTO -> {_uiState.update { currentState -> currentState.copy(departamento=input)}}
            Campo.EMAIL -> {_uiState.update { currentState -> currentState.copy(correo=input)}}
            Campo.CLAVE -> {_uiState.update { currentState -> currentState.copy(clave=input)}}
        }
    }

    fun verClave(estado: Boolean){
        estadoSaved = estado
    }

    enum class Campo{
        NOMBRE,
        CEDULA,
        TELEFONO,
        PROYECTO,
        DEPARTAMENTO,
        EMAIL,
        CLAVE
    }
}