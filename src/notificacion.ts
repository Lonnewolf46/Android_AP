import databaseQuery from "./database.js";

class Notificacion {
    id: number;
    mensaje: string;
    idColaborador: string;
    idEstado: string;

    constructor(id: number, mensaje:string, idColaborador:string, idEstado:string) {
        this.id = id;
        this.mensaje = mensaje;
        this.idColaborador = idColaborador;
        this.idEstado = idEstado;
    }

    static deserialize({id, mensaje, idColaborador, idEstado}) {
        return new Notificacion(id, mensaje, idColaborador, idEstado);
    }

    async crear() {
        await databaseQuery(`
            INSERT INTO Notificaciones(mensaje, idColaborador, idEstado)
            VALUES('${this.mensaje}', ${this.idColaborador}, ${this.idEstado})
        `);
    }
}

export default Notificacion;