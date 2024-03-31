import databaseQuery from "./database.js";
import Estado from "./estado.js";
import Proyecto from "./proyecto.js";

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

    static byId(id:number) {
        return new Tarea(id, "", 0, 0, 0, new Date(), new Date(), 0);
    }

    static deserialize({id, nombre, storyPoints, idProyecto, idEncargado, fechaInicio, fechaFin, idEstado}) {
        return new Tarea(id, nombre, storyPoints, idProyecto, idEncargado, fechaInicio, fechaFin, idEstado);
    }

    static async obtenerEstados() {
        const result = await databaseQuery(`SELECT id, estado FROM EstadosTarea`);
        return result.map(Estado.deserialize);
    }

    setProyecto(proyecto: Proyecto) {
        this.idProyecto = proyecto.id;
    }

    async crear():Promise<boolean> {
        try {
            await databaseQuery(`
                INSERT INTO Tareas(nombre, storyPoints, idProyecto, idEncargado, fechaInicio, fechaFin, idEstado)
                VALUES('${this.nombre}', ${this.storyPoints}, ${this.idProyecto}, ${this.idEncargado}, '${this.fechaInicio}', '${this.fechaFin}', ${this.idEstado})
            `);
            return true;
        } catch (error) {
            console.log("ERROR: ", error);
            return false;            
        }
    }

    async actualizar() {
        await databaseQuery(`
            UPDATE Tareas
            SET nombre='${this.nombre}', storyPoints=${this.storyPoints}, idEncargado=${this.idEncargado}
            WHERE id=${this.id}
        `);
    }

    async actualizarEstado() {
        await databaseQuery(`UPDATE Tareas SET idEstado=${this.idEstado} WHERE id=${this.id}`);
    }

    async eliminar() {
        await databaseQuery(`DELETE Tareas WHERE id=${this.id}`);
    }
}

export default Tarea;