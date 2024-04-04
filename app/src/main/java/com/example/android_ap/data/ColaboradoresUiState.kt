package com.example.android_ap.data

import com.example.android_ap.Colaborador
import com.example.android_ap.Proyecto

data class ColaboradoresUiState(
    val listaColaboradores: List<Colaborador> = listOf(),
    val listaProyectos: List<Proyecto> = listOf(),
    val idColaborador: Int = -1,
    val codigoPopUps: Int = -1,
    val codigoRespuesta: Int = -1
)
