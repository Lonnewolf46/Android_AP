import databaseQuery from "./database.js";
import Proyecto from "./proyecto.js";

class Tarea {
    id: number;
	nombre: string;
    newNombre:string;
	storyPoints: number;
	nombreProyecto: string;
	nombreEncargado: string;
	fechaInicio: Date;
    fechaFin: Date;
    estado: string;

    constructor(id:number, nombre:string,newNombre:string, storyPoints:number, nombreProyecto:string, nombreEncargado:string, fechaInicio:Date, fechaFin:Date, Estado:string){
        this.id = id;
        this.nombre = nombre;
        this.newNombre = newNombre;
        this.storyPoints = storyPoints;
        this.nombreProyecto = nombreProyecto;
        this.nombreEncargado = nombreEncargado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = Estado;
    }

    static byId(id:number) {
        return new Tarea(id, "","", 0, "", "", new Date(), new Date(), "");
    }

    static deserialize({id, nombre,newNombre, storyPoints, nombreProyecto, nombreEncargado, fechaInicio, fechaFin, estado}) {
        return new Tarea(id, nombre,newNombre, storyPoints, nombreProyecto, nombreEncargado, fechaInicio, fechaFin, estado);
    }

    setProyecto(proyecto: Proyecto) {
        this.nombreProyecto = proyecto.nombre;
    }

    async crear() {
        try {
            await databaseQuery(`
            EXEC CrearTarea 
            @Nombre = '${this.nombre}',
            @StoryPoints = ${this.storyPoints},
            @NombreProyecto ='${this.nombreProyecto}',
            @NombreEncargado ='${this.nombreEncargado}',
            @FechaInicio ='${this.fechaInicio}',
            @FechaFin ='${this.fechaFin}'
            `);
        } catch (error) {
            console.error("Error al crear la tarea:", error);
        }
    }

    async actualizar() {
        try {
            await databaseQuery(`
            EXEC updateTarea
            @inStoryPoints =${this.storyPoints},
            @inNombreTarea ='${this.nombre}',
            @inNewNombreTarea ='${this.newNombre}',
            @inNombreResponsable ='${this.nombreEncargado}',
            @inNombreProyecto='${this.nombreProyecto}'
            `);
        } catch (error) {
            console.error("Error al actualizar la tarea:", error);
        }
    }

    async actualizarEstado() {
        try {
            await databaseQuery(`
            EXEC updateEstadoTarea
            @inNombreTarea ='${this.nombre}',
            @inEstadoTarea ='${this.estado}',
            @inNombreProyecto= '${this.nombreProyecto}'
            `);
        } catch (error) {
            console.error("Error al actualizar el estado:", error);
        }
    }
}

export default Tarea;