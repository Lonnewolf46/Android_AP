package com.example.android_ap.ui

import APIAccess
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
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

class ModificarInfoPersonalViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    private var projectExpanded by mutableStateOf(false)
    private var deptExpanded by mutableStateOf(false)

    fun actualizarDatos(campo: RegistroCampos, input: String) {
        when (campo) {
            RegistroCampos.TELEFONO -> {
                _uiState.update { currentState -> currentState.copy(telefono = input) }
            }

            RegistroCampos.CORREO -> {
                _uiState.update { currentState -> currentState.copy(correo = input) }
            }

            RegistroCampos.PROYECTO -> {
                _uiState.update { currentState -> currentState.copy(proyecto = input) }
            }

            RegistroCampos.DEPARTAMENTO -> {
                _uiState.update { currentState -> currentState.copy(departamento = input) }
            }

            else -> {}
        }
    }

    fun actualizarProy(input: String) {
        actualizarDatos(RegistroCampos.PROYECTO, input)
    }

    fun actualizarDep(input: String) {
        actualizarDatos(RegistroCampos.DEPARTAMENTO, input)
    }

    fun cargarProyectos() {

        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPIProyectos()
            }

            if (resultado.isNotEmpty())
                _uiState.update { currentState -> currentState.copy(listaProyectos = resultado) }
            else
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }

            // Ahora se puede utilizar el resultado fuera del contexto de la corutina
        } catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        }
    }

    fun cargarDepartamentos() {

        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPIDeptos()
            }

            if (resultado.isNotEmpty())
                _uiState.update { currentState -> currentState.copy(listaDepartamentos = resultado) }
            else
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }

            // Ahora se puede utilizar el resultado fuera del contexto de la corutina
        } catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        }
    }

    fun subirCambios(idColaborador: Int) {
        if (_uiState.value.telefono.isNotBlank() &&
            _uiState.value.correo.isNotBlank() &&
            _uiState.value.proyecto.isNotBlank() &&
            _uiState.value.departamento.isNotBlank()
        ) {
            //Actualizar valor para no mostrar aviso
            _uiState.update { currentState -> currentState.copy(datosCorrectos = true) }
            val apiAccess = APIAccess()
            try {
                val idProyecto = _uiState.value.listaProyectos.firstOrNull { it.nombre == _uiState.value.proyecto }!!.id
                val idDepartamento = _uiState.value.listaDepartamentos.firstOrNull { it.nombre == _uiState.value.departamento }!!.id
                val resultado = runBlocking {
                    apiAccess.putAPIModificarColaborador(
                        id = idColaborador,
                        email = _uiState.value.correo,
                        telefono = _uiState.value.telefono.toInt(),
                        idProyecto = idProyecto,
                        idDepartamento = idDepartamento
                    )
                }
                if (resultado.success)
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 0) }
                else
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }

            } catch (e: IOException) {
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            } catch (e: HttpException) {
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            }

        } else {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 1) }
        }
    }

    fun cerrarEmergente() {
        _uiState.update { currentState -> currentState.copy(codigoResultado = -1) }
    }
}
