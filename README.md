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