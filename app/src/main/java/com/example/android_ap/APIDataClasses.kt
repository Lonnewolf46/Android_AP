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
    var proyecto: Proyecto
)

data class Depto(
    val id: Int,
    val nombre: String
)

data class Proyecto(
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

data class ProyectoEnviar(
    val id: Int,
    val nombre: String,
    val recursos: String,
    val presupuesto: Float,
    val idEstado: Int,
    val descripcion: String,
    val idResponsable: Int,
    val fechaInicio: String,
    val fechaFin: String,
    val tareas: List<Tarea>,
    val colaboradores: List<Int>
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
    var nombre: String,
    var storyPoints: Int,
    val idProyecto: Int,
    var idEncargado: Int?,
    val fechaInicio: String,
    var fechaFin: String,
    var idEstado: Int
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

data class Reunion(
    var tema: String,
    val fecha: String,
    val medio: String,
    val formato: String,
    val enlace: String,
    val idCreador: Int,
    val colaboradores: List<Int>
)