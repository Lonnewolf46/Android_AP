package com.example.android_ap.ui

import APIAccess
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

class RegistroViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

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

    fun actualizarProy(input: String){
        actualizarDatos(RegistroCampos.PROYECTO, input)
    }
    fun actualizarDep(input: String){
        actualizarDatos(RegistroCampos.DEPARTAMENTO, input)
    }

    fun cargarProyectos(){

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
        }catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        }
    }

    fun cargarDepartamentos(){

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
        }catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
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
     3: Error de red
     4: correo inválido
     5: Cedula es menos de 9 caracteres
     6: Telefono es menos de 8 caracteres
     7: Contraseña es menos de 4 caracteres
     */
    fun crearUsuario(){
        if (_uiState.value.nombre.isNotBlank() &&
            _uiState.value.cedula.isNotBlank() &&
            _uiState.value.telefono.isNotBlank() &&
            _uiState.value.proyecto.isNotBlank() &&
            _uiState.value.departamento.isNotBlank() &&
            _uiState.value.nombre.isNotBlank() &&
            _uiState.value.correo.isNotBlank() &&
            _uiState.value.clave.isNotBlank())
        {
            val correo = _uiState.value.correo
            val regex = Regex("""\b[A-Za-z0-9._%+-]+@(estudiantec\.cr|itcr\.ac\.cr)\b""")

            //Validacion de información
            //Correo valido
            if(!regex.matches(correo)){
                _uiState.update { currentState -> currentState.copy(codigoResultado = 4)}
            }
            //Cedula con largo minimo
            else if(_uiState.value.cedula.length < 9){
                _uiState.update { currentState -> currentState.copy(codigoResultado = 5)}
            }
            //Telefono con largo minimo
            else if(_uiState.value.telefono.length < 8){
                _uiState.update { currentState -> currentState.copy(codigoResultado = 6)}
            }
            //Contraseña con largo minimo
            else if(_uiState.value.clave.length < 4){
                _uiState.update { currentState -> currentState.copy(codigoResultado = 7)}
            }
            else{
                //Hacer solicitud a la BD
                val apiAccess = APIAccess()
                try {
                    val idProyecto = _uiState.value.listaProyectos.firstOrNull { it.nombre == _uiState.value.proyecto }!!.id
                    val idDepartamento = _uiState.value.listaDepartamentos.firstOrNull { it.nombre == _uiState.value.departamento }!!.id

                    val resultado = runBlocking {
                        apiAccess.postAPICrearColaborador(
                            nombre = _uiState.value.nombre,
                            cedula = _uiState.value.cedula.toInt(),
                            telefono = _uiState.value.cedula.toInt(),
                            email = _uiState.value.correo,
                            contrasenna = _uiState.value.clave,
                            idProyecto = idProyecto,
                            idDepartamento = idDepartamento
                        )
                    }

                    if (resultado.success)
                        _uiState.update { currentState -> currentState.copy(codigoResultado = 0) }
                    else
                        _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }

                    // Ahora se puede utilizar el resultado fuera del contexto de la corutina
                }catch (e: IOException) {
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
                } catch (e: HttpException) {
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
                }
            }
        }
        else{
            _uiState.update { currentState -> currentState.copy(codigoResultado = 1)}
        }
    }

    fun cerrarEmergente(){
        _uiState.update { currentState -> currentState.copy(codigoResultado = -1)}
    }

    fun resetState() {
        _uiState.value = RegistroUiState()
    }
}
