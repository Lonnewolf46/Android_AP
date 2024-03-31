package com.example.android_ap.data

import com.example.android_ap.Tarea

data class TareaUiState(
    val nombreTarea: String = "",
    val storyPoints: String = "",
    val encargado: String = "",
    val listaTareas: List<Tarea> = listOf(),
    val estado: TareaEstados = TareaEstados.PENDIENTE,
    val mostrar: Boolean = false,
    val codigoResultado: Int = -1
)