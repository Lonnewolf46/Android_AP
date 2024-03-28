import Proyecto from "./proyecto.js";
import databaseQuery from "./database.js";

class Foro {
    nombre: string;
    mensajes: Mensaje[];

    constructor(nombre: string, mensajes: Mensaje[]) {
        this.nombre = nombre;
        this.mensajes = mensajes;
    }

    static async obtenerForoInterno(proyecto: Proyecto): Promise<Foro> {
        const result = await databaseQuery(`
            EXEC ObtenerForoInterno @IdProyecto = ${proyecto.id}; 
        `);
        return new Foro(proyecto.nombre, result.map(Mensaje.deserialize));
    }
}

class Mensaje {
    id: number;
    mensaje: string;
    idProyecto: number;
    idEmisor: number;
    fecha: Date;

    constructor(id:number, mensaje:string, idProyecto:number, idEmisor:number, fecha:Date) {
        this.id = id;
        this.mensaje = mensaje;
        this.idProyecto = idProyecto;
        this.idEmisor = idEmisor;
        this.fecha = fecha;
    }

    static deserialize({id, mensaje, idProyecto, idEmisor, fecha}) {
        return new Mensaje(id, mensaje, idProyecto, idEmisor, new Date(fecha));
    }
}

export default Foro;