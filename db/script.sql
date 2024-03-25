USE zephyrdb

CREATE TABLE Departamentos (
	id INT PRIMARY KEY IDENTITY(1,1),
	nombre NVARCHAR(30) UNIQUE NOT NULL
)

CREATE TABLE EstadosProyecto (
	id INT PRIMARY KEY IDENTITY(1,1),
	estado NVARCHAR(20) UNIQUE NOT NULL
)

CREATE TABLE Proyectos (
	id INT PRIMARY KEY IDENTITY(1,1),
	nombre NVARCHAR(30) NOT NULL,
	recursos TEXT NOT NULL,
	presupuesto FLOAT NOT NULL,
	descripcion TEXT NOT NULL,
	idEstado INT NOT NULL,
	idResponsable INT NOT NULL
	FOREIGN KEY (idEstado) REFERENCES EstadosProyecto(id)
)

ALTER TABLE Proyectos
ADD CONSTRAINT fk_id_responsable_proyecto FOREIGN KEY (idResponsable) REFERENCES Colaboradores(id);

CREATE TABLE Colaboradores(
	id INT PRIMARY KEY IDENTITY(1,1),
	nombre NVARCHAR(100),
	cedula INT NOT NULL,
	telefono INT NOT NULL,
	email NVARCHAR(30) NOT NULL,
	contrasenna NVARCHAR(20) NOT NULL,
	idProyecto INT,
	idDepartamento INT NOT NULL
	FOREIGN KEY (idDepartamento) REFERENCES Departamentos(id),
	FOREIGN KEY (idProyecto) REFERENCES Proyectos(id)
)

CREATE TABLE Tareas(
	id INT PRIMARY KEY IDENTITY(1,1),
	nombre VARCHAR(50),
	storyPoints INT,
	idProyecto INT,
	idEncargado INT,
	fechaInicio DATETIME,
	fechaFin DATETIME
	FOREIGN KEY (idEncargado) REFERENCES Colaboradores(id),
	FOREIGN KEY (idProyecto) REFERENCES Proyectos(id)
)

CREATE TABLE MensajesForo(
	id INT PRIMARY KEY IDENTITY(1,1),
	mensaje TEXT,
	idProyecto INT,
	idEmisor INT,
	fecha DATETIME,
	FOREIGN KEY (idEmisor) REFERENCES Colaboradores(id),
	FOREIGN KEY (idProyecto) REFERENCES Proyectos(id)
)

CREATE TABLE MensajesForoGeneral(
	id INT PRIMARY KEY IDENTITY(1,1),
	mensaje TEXT,
	idEmisor INT,
	fecha DATETIME,
	FOREIGN KEY (idEmisor) REFERENCES Colaboradores(id)
)

CREATE TABLE Reuniones(
	id INT PRIMARY KEY IDENTITY(1,1),
	fecha DATETIME,
	medio VARCHAR(30),
	formato VARCHAR(100),
	enlace TEXT,
	idCreador INT,
	idProyecto INT
	FOREIGN KEY (idCreador) REFERENCES Colaboradores(id),
	FOREIGN KEY (idProyecto) REFERENCES Proyectos(id)
)

CREATE TABLE AsistentesReunion(
	id INT PRIMARY KEY IDENTITY(1,1),
	idAsistente INT,
	idReunion INT
	FOREIGN KEY (idAsistente) REFERENCES Colaboradores(id),
	FOREIGN KEY (idReunion) REFERENCES Reuniones(id)
)

CREATE TABLE EstadosNotificacion(
	id INT PRIMARY KEY IDENTITY(1,1),
	estado VARCHAR(30)
)

CREATE TABLE Notificaciones(
	id INT PRIMARY KEY IDENTITY(1,1),
	mensaje TEXT,
	idColaborador INT,
	idEstado INT
	FOREIGN KEY (idEstado) REFERENCES EstadosNotificacion(id)
)

/*
ALTER TABLE Proyectos
DROP CONSTRAINT fk_id_responsable_proyecto

DROP TABLE Colaboradores
DROP TABLE Proyectos

INSERT INTO Departamentos(nombre) VALUES('Desarrollo')
INSERT INTO Departamentos(nombre) VALUES('Recursos Humanos')

INSERT INTO EstadosProyecto(estado) VALUES('En desarrollo')

insert into Colaboradores (nombre, cedula, telefono, email, contrasenna, idProyecto, idDepartamento) values
('Langsdon Mickelwright', 239626712, 72023128, 'lmickelwright0@estudiantec.cr', 'nU7})#G?', NULL, 1);

insert into Proyectos (nombre, recursos, presupuesto, descripcion, idEstado, idResponsable)
values ('Buzzshare', 'Maternal care for Anti-A sensitization, first trimester, fetus 1', 60.63, 'Maternal care for Anti-A sensitization, first tri, fetus 1', 1, 3);
*/