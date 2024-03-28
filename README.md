# API AP

## Creación de colaboradores (registro)

**Método:** POST

**Ruta:** /api/colaboradores

**Descripción:** Registra un nuevo colaborador

#### Parámetros de consulta:
- `nombre`: string
- `cedula`: number
- `telefono`: number
- `email`: string
- `contrasenna`: string
- `idProyecto`: number
- `idDepartamento`: number

#### Ejemplo de solicitud
```json
{
  "nombre": "Griz Marthen",
  "cedula": 19670313,
  "telefono": 67049983,
  "email": "gmarthen1@estudiantec.cr",
  "contrasenna": "jG1/a#_",
  "idProyecto": 2,
  "idDepartamento": 1
}
```

#### Ejemplo de respuesta
```json
{"success": true}
```


## Validación de credenciales

**Método:** POST

**Ruta:** /api/credenciales

**Descripción:** Valida las credenciales de un colaborador, si existe retorna el id del colaborador

#### Parámetros de consulta:
- `email`: string
- `contrasenna`: string

#### Ejemplo de solicitud
```json
{
  "email": "gmarthen1@estudiantec.cr",
  "contrasenna": "jG1/a#_"
}
```

#### Ejemplo de respuesta
```json
{
  "success": true,
  "colaborador": {
    "id": 3,
    "nombre": "Langsdon Mickelwright",
    "cedula": 239626712,
    "telefono": 72023128,
    "email": "lmickelwright0@estudiantec.cr"
}
```


## Modificación de datos de colaborador

**Método:** PUT

**Ruta:** /api/colaboradores/<id_colaborador>

**Descripción:** Modifica los datos de un colaborador

#### Parámetros de consulta:
  - `telefono`: number
  - `idProyecto`: number
  - `idDepartamento`: number
  - `email`: string

#### Ejemplo de solicitud
```json
{
  "telefono": 72023128,
  "email": "lmickelwright0@estudiantec.cr",
  "idProyecto": 2,
  "idDepartamento": 1
}
```

#### Ejemplo de respuesta
```json
{"success": true}
```


## Obtención de departamentos

**Método:** GET

**Ruta:** /api/departamentos

**Descripción:** Devuelve los datos de los departamentos

#### Ejemplo de respuesta
```json
[
  {
    "id": 1,
    "nombre": "Desarrollo"
  },
  {
    "id": 2,
    "nombre": "Recursos Humanos"
  }
]
```



## Obtención de proyectos

**Método:** GET

**Ruta:** /api/proyectos

**Descripción:** Devuelve los datos de los proyectos

#### Ejemplo de respuesta
```json
[
  {
    "id": 2,
    "nombre": "Buzzshare",
    "recursos": "Maternal care for Anti-A sensitization, first trimester, fetus 1",
    "presupuesto": 60.63,
    "idEstado": 1,
    "descripcion": "Maternal care for Anti-A sensitization, first tri, fetus 1",
    "idResponsable": 3,
    "fechaInicio": "2024-03-25T15:57:13.770Z",
    "fechaFin": "2024-03-25T15:57:13.770Z"
  }
]
```


## Creación de proyectos

**Método:** POST

**Ruta:** /api/proyectos

**Descripción:** Crea un nuevo proyecto, con tareas y asignación de colaboradores

#### Parámetros de consulta:
- `nombre`: string
- `recursos`: string
- `presupuesto`: number
- `idEstado`: number
- `descripcion`: string
- `idResponsable`: number
- `fechaInicio`: string
- `fechaFin`: string
- `tareas`: tarea[]
- `colaboradores`: number[]

#### Ejemplo de solicitud
```json
{
  "nombre": "Cafetería",
  "recursos": "Materiales de construcción",
  "presupuesto": 10000,
  "idEstado": 1,
  "descripcion": "Local para vender café",
  "idResponsable": 4,
  "fechaInicio": "2024-03-25",
  "fechaFin": "2024-03-26",
  "tareas": [{
    "nombre": "Construir local",
    "storyPoints": 12,
    "idEncargado": 4,
    "fechaInicio": "2024-03-25",
    "idEstado": 1,
    "fechaFin": "2024-03-26"
  }],
  "colaboradores": [4]
}
```

#### Ejemplo de respuesta
```json
{"success": true}
```



## Notificaciones de colaborador

**Método:** GET

**Ruta:** /api/colaboradores/<id_colaborador>/notificaciones

**Descripción:** Devuelve las notificaciones destinadas a un colaborador

#### Ejemplo de respuesta
```json
[
  {
    "id": 3,
    "mensaje": "Prueba de notificación",
    "idColaborador": 3,
    "idEstado": 1
  },
  {
    "id": 4,
    "mensaje": "Otra prueba de notificación",
    "idColaborador": 3,
    "idEstado": 1
  }
]
```



## Reasignación de proyecto de colaborador

**Método:** PUT

**Ruta:** /colaboradores/<id_colaborador>/reasignar-proyecto

**Descripción:** Modifica el proyecto al que está asociado un colaborador

#### Parámetros de consulta:
  - `idProyecto`: number

#### Ejemplo de solicitud
```json
{"idProyecto": 10}
```

#### Ejemplo de respuesta
```json
{"success": true}
```


## Colaboradores sin proyecto

**Método:** GET

**Ruta:** /api/colaboradores/sin-proyecto

**Descripción:** Devuelve los colaboradores que no tienen un proyecto asociado

#### Ejemplo de respuesta
```json
[
  {
    "id": 5,
    "nombre": "pedro",
    "cedula": 204104321,
    "telefono": 84162746,
    "email": "kassinlas@estudiantec.cr",
    "idDepartamento": 1
  }
]
```



## Colaboradores de proyecto

**Método:** GET

**Ruta:** /api/proyectos/<id_proyecto>/colaboradores

**Descripción:** Devuelve los colaboradores asociados a un proyecto

#### Ejemplo de respuesta
```json
[
  {
    "id": 4,
    "nombre": "Griz Marthen",
    "cedula": 19670313,
    "telefono": 67049983,
    "email": "gmarthen1@estudiantec.cr",
    "idProyecto": 10,
    "idDepartamento": 1
  },
  {
    "id": 6,
    "nombre": "john",
    "cedula": 123456789,
    "telefono": 88907645,
    "email": "correoexample@estudiantec.cr",
    "idProyecto": 10,
    "idDepartamento": 2
  }
]
```



## Creación de notificaciones

**Método:** POST

**Ruta:** /api/notificaciones

**Descripción:** Crea una nueva notificación

#### Parámetros de consulta:
- `mensaje`: string
- `idColaborador`: number
- `idEstado`: number

#### Ejemplo de solicitud
```json
{
  "mensaje": "Prueba de notificación",
  "idColaborador": 3,
  "idEstado": 1
}
```

#### Ejemplo de respuesta
```json
{"success": true}
```



## Eliminación de colaborador en proyecto

**Método:** DELETE

**Ruta:** /proyectos/<id_proyecto>/colaboradores/<id_colaborador>

**Descripción:** Elimina la asociasión de un colaborador a un proyecto

#### Ejemplo de respuesta
```json
{"success": true}
```



## Obtención de tareas de proyecto

**Método:** GET

**Ruta:** /api/proyectos/<id_proyecto>/tareas

**Descripción:** Devuelve las tareas de un proyecto

#### Ejemplo de respuesta
```json
[
  {
    "nombre": 7,
    "storyPoints": 12,
    "idProyecto": 10,
    "idEncargado": 4,
    "fechaInicio": "1905-06-20T00:00:00.000Z",
    "fechaFin": "1905-06-19T00:00:00.000Z",
    "idEstado": 1
  }
]
```


## Creación de tareas de proyecto

**Método:** POST

**Ruta:** /api/proyectos/<id_proyecto>/tareas

**Descripción:** Crea tareas y las asocia a un proyecto existente

#### Parámetros de consulta:
- `nombre`: string
- `storyPoints`: number
- `idEncargado`: number
- `idEstado`: number
- `fechaInicio`: string
- `fechaFin`: string

#### Ejemplo de solicitud
```json
{
  "nombre": "Construir local",
  "storyPoints": 12,
  "idEncargado": 4,
  "fechaInicio": "2024-03-25",
  "idEstado": 1,
  "fechaFin": "2024-03-26"
}
```

#### Ejemplo de respuesta
```json
{"success": true}
```



## Modificación de tarea

**Método:** PUT

**Ruta:** /api/proyectos/<id_proyecto>/tareas/<id_tarea>

**Descripción:** Modifica los datos de una tarea

#### Parámetros de consulta:
  - `nombre`: string
  - `storyPoints`: number
  - `idEncargado`: number

#### Ejemplo de solicitud
```json
{
  "nombre": "Construir local",
  "storyPoints": 22,
  "idEncargado": 4
}
```

#### Ejemplo de respuesta
```json
{"success": true}
```