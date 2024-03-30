package com.example.android_ap.ui

import APIAccess
import APIlogin
import androidx.lifecycle.ViewModel
import com.example.android_ap.data.InicioSesionCampos
import com.example.android_ap.data.InicioSesionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

class InicioSesionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InicioSesionUiState())
    val uiState: StateFlow<InicioSesionUiState> = _uiState.asStateFlow()


    fun actualizarInfo(campo: InicioSesionCampos, usuarioInput: String) {
        when (campo) {
            InicioSesionCampos.NOMBRE -> _uiState.update { currentState -> currentState.copy(usuario = usuarioInput) }
            InicioSesionCampos.CLAVE -> _uiState.update { currentState -> currentState.copy(clave = usuarioInput) }
        }
    }

    fun verClave(estado: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                claveVisible = estado
            )
        }
    }

    /**
    CODIGOS:
    -1: Por defecto
    0: Proceso correcto
    1: Quedan campos vacios
    2: Credenciales incorrectas
     */
    fun validarCampos(): APIlogin? {
        if (_uiState.value.usuario != "" &&
            _uiState.value.clave != ""
        ) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 0) }//Campos llenos

            val apiAccess = APIAccess()

            // Llamada desde una función suspendida, como una función de corutina
            try {
                val resultado = runBlocking {
                    apiAccess.postAPIlogin(_uiState.value.usuario, _uiState.value.clave)
                }
                _uiState.update { currentState -> currentState.copy(loginExitoso = resultado.success) }
                if (!resultado.success)
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 2) }

                return resultado
                // Ahora se puede utilizar el resultado fuera del contexto de la corutina
            }catch (e: IOException) {
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            } catch (e: HttpException) {
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            }

        } else _uiState.update { currentState -> currentState.copy(codigoResultado = 1) }
        return null
    }

    fun cerrarEmergente() {
        _uiState.update { currentState -> currentState.copy(codigoResultado = -1) }
    }


    fun resetState() {
        _uiState.value = InicioSesionUiState()
    }
}