import databaseQuery from "./database.js";

class Tarea {
    id: number;
	nombre: string;
	storyPoints: number;
	idProyecto: number;
	idEncargado: number;
	fechaInicio: Date;
    fechaFin: Date;
    idEstado: number;

    constructor(id:number, nombre:string, storyPoints:number, idProyecto:number, idEncargado:number, fechaInicio:Date, fechaFin:Date, idEstado:number) {
        this.id = id;
        this.nombre = nombre;
        this.storyPoints = storyPoints;
        this.idProyecto = idProyecto;
        this.idEncargado = idEncargado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idEstado = idEstado;
    }

    static deserialize({id, nombre, storyPoints, idProyecto, idEncargado, fechaInicio, fechaFin, idEstado}) {
        return new Tarea(id, nombre, storyPoints, idProyecto, idEncargado, fechaInicio, fechaFin, idEstado);
    }

    async crear() {
        await databaseQuery(`
            INSERT INTO Tareas(nombre, storyPoints, idProyecto, idEncargado, fechaInicio, fechaFin, idEstado)
            VALUES('${this.nombre}', ${this.storyPoints}, ${this.idProyecto}, ${this.idEncargado}, ${this.fechaInicio}, ${this.fechaFin}, ${this.idEstado})
        `);
    }
}

export default Tarea;