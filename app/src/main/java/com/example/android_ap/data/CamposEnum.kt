package com.example.android_ap.data

enum class InicioSesionCampos {
        NOMBRE,
        CLAVE
}
enum class RegistroCampos{
        NOMBRE,
        CEDULA,
        TELEFONO,
        PROYECTO,
        DEPARTAMENTO,
        CORREO,
        CLAVE
}

enum class TareaEstados{
        PENDIENTE,
        PROGRESO,
        COMPLETADA
}
enum class TareaCampos{
        NOMBRE,
        STORYPOINTS,
        ENCARGADO,
        ESTADO
}

enum class ReunionCampos{
        TEMA,
        FECHA,
        MEDIO,
        FORMATO,
        DETALLES
}

enum class ProyectoCampos{
        NOMBRE,
        RECURSOS,
        PRESUPUESTO,
        ESTADO,
        DESCRIPCION,
        RESPONSABLE,
}