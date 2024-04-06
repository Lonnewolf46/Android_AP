package com.example.android_ap.data

import com.example.android_ap.Colaborador
import com.example.android_ap.Estado
import com.example.android_ap.Tarea

data class ProyectoUiState(
    val nombre: String = "",
    val recursos: String = "",
    val presupuesto: String = "",
    val estado: String = "",
    val descripcion: String = "",
    val responsable: String = "",
    val codigoResultado: Int = -1,
    val listaEstadosProyecto: List<Estado> = listOf(),
    val listaColaboradores: List<Colaborador> = listOf(),
    val listaColaboradoresElegidos: List<Int> = listOf(),
    val idResponsable: Int = -1,
    val listaTareas: List<Tarea> = listOf(),
    val listaEstadosTarea: List<Estado> = listOf(),

    val nombreTarea: String = "",
    val storyPointsTarea: String = "",
    val fechaFinTarea: String = "",
    val encargadoTarea: String = "",
    val estadoTarea: String  = "",
    val codigoResultadoTarea: Int = -1

    )
