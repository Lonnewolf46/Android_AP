package com.example.android_ap.data

data class RegistroUiState(
    val nombre: String = "",
    val cedula: String = "",
    val telefono: String = "",

    //Posiblemente se cambien cuando se haga la implementación
    val proyecto: String = "",
    val departamento: String = "",
    //Fin cambios

    val correo: String = "",
    val clave: String = "",
    val claveVisible: Boolean = false
)
