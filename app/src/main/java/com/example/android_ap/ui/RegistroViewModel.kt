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

class RegistroViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    private var projectExpanded by mutableStateOf(false)
    private var deptExpanded by mutableStateOf(false)

    /**
    Actualiza la informacion en uiState segun cada evento
     */
    fun actualizarDatos(campo: RegistroCampos, input: String){
        when(campo){
            RegistroCampos.NOMBRE -> {_uiState.update { currentState -> currentState.copy(nombre=input)}}
            RegistroCampos.CEDULA -> {_uiState.update { currentState -> currentState.copy(cedula=input)}}
            RegistroCampos.TELEFONO -> {_uiState.update { currentState -> currentState.copy(telefono=input)}}
            RegistroCampos.PROYECTO -> {_uiState.update { currentState -> currentState.copy(proyecto=input)}}
            RegistroCampos.DEPARTAMENTO -> {_uiState.update { currentState -> currentState.copy(departamento=input)}}
            RegistroCampos.CORREO -> {_uiState.update { currentState -> currentState.copy(correo=input)}}
            RegistroCampos.CLAVE -> {_uiState.update { currentState -> currentState.copy(clave=input)}}
        }
    }

    /**
    Alterna la visibilidad de la contraseña
     */
    fun verClave(estado: Boolean){
        _uiState.update { currentState -> currentState.copy(
            claveVisible = estado
        )}
    }

    /**
    Valida que el uiState tenga información en todos sus campos. Si no es asi, avisa.
     CODIGOS:
     -1: Por defecto
     0: Proces correcto
     1: Quedan campos vacios
     2: correo inválido
     3: Cedula es menos de 9 caracteres
     4: Telefono es menos de 8 caracteres
     5: Contraseña es menos de 4 caracteres
     */
    fun validarCampos(){
        if (_uiState.value.nombre!="" &&
            _uiState.value.cedula!="" &&
            _uiState.value.telefono!="" &&
            _uiState.value.nombre!="" &&
            _uiState.value.correo!="" &&
            _uiState.value.clave!="")
        {
            val correo = _uiState.value.correo
            val regex = Regex("""\b[A-Za-z0-9._%+-]+@(estudiantec\.cr|itcr\.ac\.cr)\b""")
            _uiState.update { currentState -> currentState.copy(datosCorrectos = false)}

            //Validacion de información
            //Correo valido
            if(!regex.matches(correo)){
                _uiState.update { currentState -> currentState.copy(codigoResultado = 2)}
            }
            //Cedula con largo minimo
            else if(_uiState.value.cedula.length < 9){
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3)}
            }
            //Telefono con largo minimo
            else if(_uiState.value.telefono.length < 8){
                _uiState.update { currentState -> currentState.copy(codigoResultado = 4)}
            }
            //Contraseña con largo minimo
            else if(_uiState.value.clave.length < 4){
                _uiState.update { currentState -> currentState.copy(codigoResultado = 5)}
            }
            else{
                //Actualizar valor para no mostrar aviso
                _uiState.update { currentState -> currentState.copy(datosCorrectos = true)}
                _uiState.update { currentState -> currentState.copy(codigoResultado = 0)}

                //Hacer solicitud a la BD
                /*TODO*/
            }

        }
        else{
            _uiState.update { currentState -> currentState.copy(datosCorrectos = false)}
            _uiState.update { currentState -> currentState.copy(codigoResultado = 1)}
        }
    }

    fun cerrarEmergente(){
        _uiState.update { currentState -> currentState.copy(datosCorrectos = true)}
        _uiState.update { currentState -> currentState.copy(codigoResultado = -1)}
    }

    fun resetState() {
        _uiState.value = RegistroUiState()
    }
}
