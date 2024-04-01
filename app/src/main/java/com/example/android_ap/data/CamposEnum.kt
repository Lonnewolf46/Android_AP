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

enum class TareaCampos{
        NOMBRE,
        STORYPOINTS,
        ENCARGADO,
        ESTADO,
        FECHAFIN
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

enum class UsuarioInfoCampos{
        IDENTIFICADOR,
        NOMBRE,
        TELEFONO,
        IDPROYECTO,
        PROYECTO,
        DEPARTAMENTO,
        CORREO,
        EMAIL
}