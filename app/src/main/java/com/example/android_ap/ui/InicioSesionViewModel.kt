package com.example.android_ap.ui

import androidx.lifecycle.ViewModel
import com.example.android_ap.data.InicioSesionCampos
import com.example.android_ap.data.InicioSesionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InicioSesionViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(InicioSesionUiState())
    val uiState: StateFlow<InicioSesionUiState> = _uiState.asStateFlow()


    fun actualizarInfo(campo: InicioSesionCampos, usuarioInput: String){
        when(campo){
            InicioSesionCampos.NOMBRE -> _uiState.update { currentState -> currentState.copy(usuario = usuarioInput) }
            InicioSesionCampos.CLAVE -> _uiState.update { currentState -> currentState.copy(clave = usuarioInput) }
        }
    }

    fun verClave(estado: Boolean){
        _uiState.update { currentState -> currentState.copy(
            claveVisible = estado
        )}
    }

    fun validarCampos(){
        if(_uiState.value.usuario != "" &&
            _uiState.value.clave != "")
        {
            _uiState.update { currentState -> currentState.copy(camposLlenos = true,primerInicio = false)}
            //Hacer algo más

        }
        else _uiState.update { currentState -> currentState.copy(camposLlenos = false,primerInicio = false)}
    }
    fun cerrarEmergente(){
        _uiState.update { currentState -> currentState.copy(camposLlenos = true)}
    }

    fun resetState() {
        _uiState.value = InicioSesionUiState()
    }

}