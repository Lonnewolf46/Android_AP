import databaseQuery from "../services/database.js";

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
        try {
            await databaseQuery(`
                EXEC CrearNotificacion 
                    @Mensaje = '${this.mensaje}',
                    @IdColaborador = ${this.idColaborador}, 
                    @IdEstado =  ${this.idEstado}; 
            `);
        } catch (error) {
            console.error(error);
        }
    }
}

export default Notificacion;