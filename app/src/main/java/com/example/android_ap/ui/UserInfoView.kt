package com.example.android_ap.ui

import androidx.lifecycle.ViewModel
import com.example.android_ap.data.UsuarioActualInfo
import com.example.android_ap.data.UsuarioInfoCampos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserInfoView:ViewModel() {
    private val _uiState = MutableStateFlow(UsuarioActualInfo())
    val uiState: StateFlow<UsuarioActualInfo> = _uiState.asStateFlow()

    fun actualizarInfo(campo: UsuarioInfoCampos, input: String){
        when(campo){
            UsuarioInfoCampos.IDENTIFICADOR -> _uiState.update { currentState -> currentState.copy(id = input.toInt()) }
            UsuarioInfoCampos.NOMBRE -> _uiState.update { currentState -> currentState.copy(nombre = input) }
            UsuarioInfoCampos.TELEFONO -> _uiState.update { currentState -> currentState.copy(telefono = input) }
            UsuarioInfoCampos.PROYECTO -> _uiState.update { currentState -> currentState.copy(proyecto = input) }
            UsuarioInfoCampos.DEPARTAMENTO -> _uiState.update { currentState -> currentState.copy(departamento = input) }
            UsuarioInfoCampos.CORREO -> _uiState.update { currentState -> currentState.copy(correo = input) }
            UsuarioInfoCampos.EMAIL -> _uiState.update { currentState -> currentState.copy(correo = input) }
        }

    }

}