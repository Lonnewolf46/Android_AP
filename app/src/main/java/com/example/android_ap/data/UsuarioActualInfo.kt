package com.example.android_ap.data

data class UsuarioActualInfo(
    var id: Int = -1,
    var nombre: String = "",
    val telefono: String = "",
    val proyecto: String = "",
    val departamento: String = "",
    val correo: String = "",
    val admin: Boolean = false
)