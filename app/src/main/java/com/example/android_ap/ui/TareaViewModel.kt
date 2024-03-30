package com.example.android_ap.ui

import androidx.lifecycle.ViewModel
import com.example.android_ap.data.TareaCampos
import com.example.android_ap.data.TareaEstados
import com.example.android_ap.data.TareaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TareaViewModel: ViewModel(){
    private val _uiState = MutableStateFlow(TareaUiState())
    val uiState: StateFlow<TareaUiState> = _uiState.asStateFlow()

    /*TODO: Implementar un método que de única ejecución que llene los campos de Tarea,
    *  para luego desplegarlos en pantalla en la modificación de tareas*/

    /**
    Actualiza la informacion en uiState segun cada evento
     */
    fun ActualizarCampos(campo: TareaCampos, input: String){
        when (campo){
            TareaCampos.NOMBRE -> {_uiState.update{currentState -> currentState.copy(nombreTarea = input)}}
            TareaCampos.STORYPOINTS -> {_uiState.update{currentState -> currentState.copy(storyPoints = input)}}
            TareaCampos.ENCARGADO -> {_uiState.update{currentState -> currentState.copy(encargado = input)}}
            TareaCampos.ESTADO -> {_uiState.update{currentState -> currentState.copy(estado = StringToEstado(input))}}
        }
    }

    fun ActualizarEncargado(input: String){
        ActualizarCampos(TareaCampos.ENCARGADO, input)
    }

    /**
     Funcion para crear una tarea
     CODIGOS
     -1: Por defecto
     0: Correcto
     8: Nombre de tarea incorrecto
     9: StoryPoints incorrecto
     10: Encargado no seleccionado
    */
    fun CrearTarea() {
        if (_uiState.value.nombreTarea.isBlank()) {
            _uiState.update{currentState -> currentState.copy(codigoResultado = 8)}
        }
        else if(_uiState.value.storyPoints.isBlank())
            _uiState.update{currentState -> currentState.copy(codigoResultado = 9)}

        else if(_uiState.value.encargado.isBlank())
            _uiState.update{currentState -> currentState.copy(codigoResultado = 10)}
        else{
            //Aqui van acciones para crear tarea nueva



            //Crear exitoso, se cierra ventana
            _uiState.update{currentState -> currentState.copy(codigoResultado = 0)}
            _uiState.update{currentState -> currentState.copy(mostrar = false)}
        }
    }

    fun HacerVisible(){
        _uiState.update{currentState -> currentState.copy(mostrar = true)}
    }

    fun resetState() {
        _uiState.value = TareaUiState()
    }
}

/**
Convierte al string en un enum de Estado
 */
private fun StringToEstado(texto: String): TareaEstados{
    return when(texto){
        "En progreso" -> TareaEstados.PROGRESO
        "Completada" -> TareaEstados.COMPLETADA
        else -> TareaEstados.PENDIENTE
    }
}
