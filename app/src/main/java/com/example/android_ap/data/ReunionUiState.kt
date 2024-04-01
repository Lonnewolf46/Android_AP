package com.example.android_ap.data

import com.example.android_ap.Colaborador

data class ReunionUiState(
    val tema: String = "",
    val fecha: String = "",
    val medio: String = "",
    val formato: String = "",
    val detalles: String = "",
    val listaColaboradores: List<Colaborador> =  listOf(),
    val listaColaboradoresElegidos: MutableList<Int> = mutableListOf(),
    val verAsignar: Boolean = false,
    val codigoResultado: Int = -1
)
