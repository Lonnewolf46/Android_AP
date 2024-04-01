package com.example.android_ap

open class BaseClass
open class ForumMessageClass

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
):BaseClass()

data class Response(
    val success: Boolean
)

data class Notificacion(
    val id: Int,
    val mensaje: String,
    val idColaborador: Int,
    val idEstado: Int
)

data class Tarea(
    val id: Int,
    val nombre: String,
    val storyPoints: Int,
    val idProyecto: Int,
    val idEncargado: Int,
    val fechaInicio: String,
    val fechaFin: String,
    val idEstado: Int
)

data class Colaborador(
    val id: Int,
    val nombre: String,
    val cedula: Int,
    val telefono: Int,
    val email: String,
    val idProyecto: Int,
    val idDepartamento: Int
):BaseClass()

data class Estado(
    val id: Int,
    val estado: String
)

data class MensajeForoGeneral(
    val id: Int,
    val mensaje: String,
    val idEmisor: Int,
    val fecha: String
):ForumMessageClass()

data class ForoGeneral(
    val nombre: String,
    val mensajes: List<MensajeForoGeneral>
)

data class MensajeForoProyecto(
    val id: Int,
    val mensaje: String,
    val idProyecto: Int,
    val idEmisor: Int,
    val fecha: String
):ForumMessageClass()

data class ForoProyecto(
    val nombre: String,
    val mensajes: List<MensajeForoProyecto>
)