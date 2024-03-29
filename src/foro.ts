import Proyecto from "./proyecto.js";
import databaseQuery from "./database.js";

class Foro {
    nombre: string;
    mensajes: Mensaje[];

    constructor(nombre: string, mensajes: Mensaje[]) {
        this.nombre = nombre;
        this.mensajes = mensajes;
    }

    static async obtenerForo(proyecto?: Proyecto): Promise<Foro> {
        if(proyecto && proyecto.id && typeof proyecto.id === "number") {
            return await ForoInterno.obtenerForo(proyecto);
        }
        return await ForoGeneral.obtenerForo();
    }

    async guardarMensaje(mensaje:Mensaje) {}
}

class ForoInterno extends Foro {
    id:number;
    
    static byId(id:number) {
        const foro = new ForoInterno("", []);
        foro.id = id;
        return foro;
    }
    
    static async obtenerForo(proyecto: Proyecto): Promise<ForoInterno> {
        const result = await databaseQuery(`EXEC ObtenerForoInterno @IdProyecto = ${proyecto.id};`);
        return new ForoInterno(proyecto.nombre, result.map(Mensaje.deserialize));
    }

    async guardarMensaje(mensaje:Mensaje) {
        await databaseQuery(`
            INSERT INTO MensajesForo (mensaje, idProyecto, idEmisor)
            VALUES ('${mensaje.mensaje}', ${this.id}, ${mensaje.idEmisor})
        `);
    }
}

class ForoGeneral extends Foro {
    static new() {
        return new ForoGeneral("", []);
    }

    static async obtenerForo():Promise<ForoGeneral> {
        const result = await databaseQuery(`SELECT id, mensaje, idEmisor, fecha FROM MensajesForoGeneral`);
        return new Foro("Foro General", result.map(Mensaje.deserialize));
    }

    async guardarMensaje(mensaje:Mensaje) {
        const date = new Date();
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        const today = `${year}-${month}-${day}`;
        await databaseQuery(`
            INSERT INTO MensajesForoGeneral (mensaje, idEmisor, fecha)
            VALUES ('${mensaje.mensaje}', ${mensaje.idEmisor}, '${today}')
        `);
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
export { ForoInterno, ForoGeneral, Mensaje };