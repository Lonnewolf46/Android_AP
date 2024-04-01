package com.example.android_ap.data

import com.example.android_ap.Colaborador
import com.example.android_ap.Estado
import com.example.android_ap.Tarea

data class TareaUiState(
    val nombreTarea: String = "",
    val storyPoints: String = "",
    val encargado: String = "",
    val fechaFin: String = "",
    val estado: String = "",
    val listaTareas: List<Tarea> = listOf(),
    val listaColaboradores: List<Colaborador> = listOf(),
    val listaEstados: List<Estado> = listOf(),
    val mostrar: Boolean = false,
    val codigoResultado: Int = -1
)