import Colaborador from "./colaborador.js";
import databaseQuery from "./database.js";
import Tarea from "./tarea.js";

class Proyecto {
    id: number;
    nombre: string;
    recursos: string;
    presupuesto: number;
    idEstado: number;
    descripcion: string;
    idResponsable: number;
    fechaInicio: Date;
    fechaFin: Date;
    tareas: Tarea[];
    colaboradores: Colaborador[];

    constructor(
        id:number, nombre:string, recursos:string, presupuesto:number, idEstado:number, descripcion:string,
        idResponsable:number, fechaInicio:Date, fechaFin:Date, tareas:Tarea[], colaboradores: Colaborador[]
    ) {
        this.id = id;
        this.nombre = nombre;
        this.recursos = recursos;
        this.presupuesto = presupuesto;
        this.idEstado = idEstado;
        this.descripcion = descripcion;
        this.idResponsable = idResponsable;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tareas = tareas;
        this.colaboradores = colaboradores;
    }

    static byId(id:number) {
        return new Proyecto(id, "", "", 0, 0, "", 0, new Date(), new Date(), [], []);
    }

    static byData(
        nombre:string, recursos:string, presupuesto:number, idEstado:number, descripcion:string,
        idResponsable:number, fechaInicio:Date, fechaFin:Date, tareas:Tarea[], colaboradores: Colaborador[]
    ) {
        return new Proyecto(0, nombre, recursos, presupuesto, idEstado, descripcion, idResponsable, fechaInicio, fechaFin, tareas, colaboradores);
    }
    
    static deserialize({id, nombre, recursos, presupuesto, idEstado, descripcion, idResponsable, fechaInicio, fechaFin, tareas, colaboradores}) {
        return new Proyecto(id, nombre, recursos, presupuesto, idEstado, descripcion, idResponsable, fechaInicio, fechaFin, tareas || [], colaboradores || []);
    }

    static async obtenerProyectos():Promise<Proyecto[]> {
        return (await databaseQuery(`EXEC getProyectos`)).map(Proyecto.deserialize);
    }

    serialize() {
        return {
            id: this.id,
            nombre: this.nombre,
            recursos: this.recursos,
            presupuesto: this.presupuesto,
            idEstado: this.idEstado,
            descripcion: this.descripcion,
            idResponsable: this.idResponsable,
            fechaInicio: this.fechaInicio,
            fechaFin: this.fechaFin
        }
    }

    async crear() {
        const resultado = await databaseQuery(`EXEC postProyectos @nombre='${this.nombre}', @recursos='${this.recursos}', @presupuesto=${this.presupuesto}
        ,@descripcion='${this.descripcion}', @idResponsable=${this.idResponsable},@fechaInicio='${this.fechaInicio}'
        ,@fechaFin='${this.fechaFin}'`);
        const idProyecto = resultado[0].NuevoProyectoID;

        this.tareas.forEach(async tarea => {
            tarea.idProyecto = idProyecto;
            await tarea.crear();
        });
        this.colaboradores.forEach(colaborador => {
            colaborador.reasignarProyecto(idProyecto);
        });
    }

    async obtenerColaboradores():Promise<Colaborador[]> {
        const result = await databaseQuery(`
            SELECT id, nombre, cedula, telefono, email, idProyecto, idDepartamento
            FROM Colaboradores
            WHERE idProyecto=${this.id}
        `);
        return result.map(Colaborador.deserialize);
    }

    async eliminarColaborador(colaborador: Colaborador) {
        await databaseQuery(`UPDATE Colaboradores SET idProyecto=NULL WHERE id=${colaborador.id}`);
    }
}

export default Proyecto;