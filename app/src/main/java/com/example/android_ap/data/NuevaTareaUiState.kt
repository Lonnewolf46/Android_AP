package com.example.android_ap.data

data class TareaUiState(
    val nombreTarea: String = "",
    val storyPoints: Int = 0,
    val encargado: String = "",
    val estado: TareaEstados = TareaEstados.PROGRESO,
    val mostrar: Boolean = false
)