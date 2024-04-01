package com.example.android_ap.ui

import APIAccess
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android_ap.data.ForoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

class ForoViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ForoUiState())
    val uiState: StateFlow<ForoUiState> = _uiState.asStateFlow()

    fun actualizarMensaje(input: String){
        _uiState.update { currentState -> currentState.copy(mensajeActual = input) }
    }

    fun cargarForoGeneral(){
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPIMensajeForoGen()
            }
            if (resultado.nombre.isNotBlank()) {
                _uiState.update { currentState -> currentState.copy(mensajesForoGen = resultado.mensajes) }
            }
            else
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        }catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 2) }
        }
    }

    fun cargarForoProyecto(idProyecto: Int){
        val apiAccess = APIAccess()
        try {
            val resultado = runBlocking {
                apiAccess.getAPIMensajeForoPro(idProyecto)
            }
            Log.d("RES: ", "$resultado")
            if (resultado.nombre.isNotBlank()) {
                _uiState.update { currentState -> currentState.copy(mensajesForoPro = resultado.mensajes) }
            }
            else
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        }catch (e: IOException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
        } catch (e: HttpException) {
            _uiState.update { currentState -> currentState.copy(codigoResultado = 2) }
        }
    }

    fun subirMensajeForoGeneral(nombreUsuario: String, idUsuario: Int){
        if(_uiState.value.mensajeActual.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultado = 1) }
        else{
            val mensaje = "$nombreUsuario: ${_uiState.value.mensajeActual}"

            val apiAccess = APIAccess()
            try {
                val resultado = runBlocking {
                    apiAccess.postAPIMensajeForoGen(
                        mensaje = mensaje,
                        idEmisor = idUsuario)
                }
                if (resultado.success) {
                    this.cargarForoGeneral()
                    this.actualizarMensaje("")
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 0) }
                }
                else
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            }catch (e: IOException) {
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            } catch (e: HttpException) {
                _uiState.update { currentState -> currentState.copy(codigoResultado = -2) }
            }
        }
    }

    fun subirMensajeForoProyecto(nombreUsuario: String, idUsuario: Int, idProyecto: Int){
        if(_uiState.value.mensajeActual.isBlank())
            _uiState.update { currentState -> currentState.copy(codigoResultado = 1) }
        else{
            val mensaje = "$nombreUsuario: ${_uiState.value.mensajeActual}"

            val apiAccess = APIAccess()
            try {
                val resultado = runBlocking {
                    apiAccess.postAPIMensajeForoPro(
                        idProyecto = idProyecto,
                        mensaje = mensaje,
                        idEmisor = idUsuario)
                }
                if (resultado.success) {
                    this.cargarForoProyecto(idProyecto)
                    this.actualizarMensaje("")
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 0) }
                }
                else
                    _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            }catch (e: IOException) {
                _uiState.update { currentState -> currentState.copy(codigoResultado = 3) }
            } catch (e: HttpException) {
                _uiState.update { currentState -> currentState.copy(codigoResultado = -2) }
            }
        }
    }

    fun cerrarEmergente(){
        _uiState.update { currentState -> currentState.copy(codigoResultado = -1) }
    }

}