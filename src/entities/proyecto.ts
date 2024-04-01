import Colaborador from "./colaborador.js";
import databaseQuery from "../services/database.js";
import Reunion from "./reunion.js";
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

    static async loadDataById(id:number):Promise<Proyecto> {
        const result = await databaseQuery(`
            SELECT
                id, nombre, recursos, presupuesto, idEstado,
                descripcion, idResponsable, fechaInicio, fechaFin
            FROM Proyectos
            WHERE id=${id}
        `);
        return Proyecto.deserialize(result[0]);
    }
    
    static deserialize({id, nombre, recursos, presupuesto, idEstado, descripcion, idResponsable, fechaInicio, fechaFin, tareas, colaboradores}) {
        return new Proyecto(id, nombre, recursos, presupuesto, idEstado, descripcion, idResponsable, fechaInicio, fechaFin, tareas || [], colaboradores || []);
    }

    static async obtenerProyectos():Promise<Proyecto[]> {
        const proyectos = (await databaseQuery(`EXEC getProyectos`)).map(Proyecto.deserialize);
        console.log(proyectos);
        return proyectos;
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
        const resultado = await databaseQuery(`
            EXEC postProyectos @nombre='${this.nombre}', @recursos='${this.recursos}',
            @presupuesto=${this.presupuesto}, @descripcion='${this.descripcion}',
            @idResponsable=${this.idResponsable}, @fechaInicio='${this.fechaInicio}',
            @fechaFin='${this.fechaFin}'`
        );
        this.id = resultado[0].NuevoProyectoID;
        this.tareas.forEach(async tarea => {
            tarea.idProyecto = this.id;
            await tarea.crear();
        });
        this.colaboradores.forEach(colaborador => {
            colaborador.reasignarProyecto(this);
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

    async obtenerTareas():Promise<Tarea[]> {
        const result = await databaseQuery(`
            SELECT id, nombre, storyPoints, idProyecto, idEncargado, fechaInicio, fechaFin, idEstado
            FROM Tareas
            WHERE idProyecto=${this.id}   
        `);
        return result.map(Tarea.deserialize);
    }

    async actualizar() {
        // Se actualiza proyecto
        await databaseQuery(`
            UPDATE Proyectos
            SET
                nombre='${this.nombre}', recursos='${this.recursos}', presupuesto=${this.presupuesto},
                idEstado=${this.idEstado}, descripcion='${this.descripcion}', idResponsable=${this.idResponsable}
            WHERE id=${this.id}
        `);

        // Se obtienen las tareas existentes actualmente del proyecto
        let tareasActuales = await this.obtenerTareas();
        // Se iteran las tareas nuevas
        await Promise.all(this.tareas.map(async tarea => {
            // Si tiene id, ya existe dentro de las actuales, se modifica
            if(tarea.id) {
                // Se descarta de las tareas actuales
                tareasActuales = tareasActuales.filter(tareaActual => tareaActual.id != tarea.id);
                await tarea.actualizar();
            } else {
                // Si no tiene id, es nueva, se crea
                await tarea.crear()
            }
        }));
        // Se iteran las tareas restantes, son las que ya no existen, se eliminan
        await Promise.all(tareasActuales.map(async tareaActual => {
            await tareaActual.eliminar();
        }));

        // Se obtienen colaboradores actuales del proyecto
        let colaboradoresActuales = await this.obtenerColaboradores();

        if(colaboradoresActuales.length) {
            // Se descartan los colaboradores actuales del proyecto
            await databaseQuery(`
               UPDATE Colaboradores
                SET idProyecto=NULL
                WHERE id in (${colaboradoresActuales.map(c => c.id).join(",")})
            `);
        }

        if(this.colaboradores.length) {
            // Se asocian a los nuevos colaboradores del proyecto
            await databaseQuery(`
                UPDATE Colaboradores
                SET idProyecto=${this.id}
               WHERE id in (${this.colaboradores.map(c => c.id).join(",")})
            `);
        }
    }

    async obtenerReuniones() {
        return (await databaseQuery(`
            SELECT id, fecha, tema, medio, formato, enlace, idCreador, idProyecto
            FROM Reuniones WHERE idProyecto=${this.id}
        `)).map(Reunion.deserialize);
    }
}

export default Proyecto;