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

    /**
    Crea una tarea despues de validar que todos los campos esten llenos.
    NOTA: Quizá haga falta un código de retorno para indicar a la interfaz si fue exitoso o no
     */
    fun CrearTarea() {
        if (_uiState.value.nombreTarea != "" && _uiState.value.storyPoints != ""){

            //Aqui van acciones para crear tarea nueva

            resetState() //Cerrar ventana
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
