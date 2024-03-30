package com.example.android_ap

data class APIlogin(
    val success:Boolean,
    var colaborador: User
)
data class User (
    var id: Int,
    var nombre: String,
    var email: String,
    var cedula: Int,
    var telefono: Int,
    var departamento: Depto,
    var proyecto: Project
)

data class Depto(
    val id: Int,
    val nombre: String
)

data class Project(
    val id: Int,
    val nombre: String,
    val recursos: String,
    val presupuesto: Float,
    val idEstado: Int,
    val descripcion: String,
    val idResponsable: Int,
    val fechaInicio: String,
    val fechaFin: String
)

data class Response(
    val success: Boolean
)


