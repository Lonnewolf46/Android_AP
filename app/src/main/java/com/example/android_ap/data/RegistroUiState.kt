package com.example.android_ap.data

import com.example.android_ap.Depto
import com.example.android_ap.Proyecto

data class RegistroUiState(
    val nombre: String = "",
    val cedula: String = "",
    val telefono: String = "",

    //Posiblemente se cambien cuando se haga la implementación
    val proyecto: String = "",
    val listaProyectos: List<Proyecto> = listOf(),

    val departamento: String = "",

    val listaDepartamentos: List<Depto> = listOf(),
    //Fin cambios

    val correo: String = "",
    val clave: String = "",
    val claveVisible: Boolean = false,
    val datosCorrectos: Boolean = true,
    val codigoResultado: Int = -1
)
