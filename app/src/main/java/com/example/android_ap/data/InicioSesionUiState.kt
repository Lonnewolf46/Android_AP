package com.example.android_ap.data

data class InicioSesionUiState(
    val usuario: String = "",
    val clave: String = "",
    val claveVisible: Boolean = false,
    val camposLlenos: Boolean = false,
    val primerInicio: Boolean = true
)