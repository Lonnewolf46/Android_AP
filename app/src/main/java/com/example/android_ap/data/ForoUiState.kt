package com.example.android_ap.data

import com.example.android_ap.MensajeForoGeneral
import com.example.android_ap.MensajeForoProyecto

data class ForoUiState(
    val mensajesForoGen: List<MensajeForoGeneral> = listOf(),
    val mensajesForoPro: List<MensajeForoProyecto> = listOf(),
    val mensajeActual: String = "",
    val codigoResultado: Int = -1
)
