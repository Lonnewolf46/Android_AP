import databaseQuery from "../services/database.js";
import sendMail from "../services/mail.js";

class Notificacion {
    
    id: number;
    mensaje: string;
    idProyecto: string;
    Fecha: Date;

    constructor(id: number, mensaje:string, idProyecto:string, Fecha:Date) {
        this.id = id;
        this.mensaje = mensaje;
        this.idProyecto = idProyecto;
        this.Fecha = Fecha;
    }

    static deserialize({id, mensaje, idProyecto, Fecha}) {
        return new Notificacion(id, mensaje, idProyecto, Fecha);
    }

    static byId(id:number) {
        return new Notificacion(id, "", "",new Date());
    }

    async crear() {
        try {
            await databaseQuery(`
                EXEC CrearNotificacion 
                    @Mensaje = '${this.mensaje}',
                    @idProyecto = ${this.idProyecto}, 
                    @Fecha =  ${this.Fecha}; 
            `);
            await sendMail(
                "testwwr81@gmail.com",
                "Creación",`Se ha generado una nueva notificación`
            )
        } catch (error) {
            console.error(error);
        }
    }
    
    static async obtenerNotificaciones(idProyecto:number):Promise<Notificacion>{
        const result = await databaseQuery(`
            EXEC  ObtenerNotificaciones @IdProyecto=${idProyecto}
        `);
        var resultado=result.map(Notificacion.deserialize)
        await sendMail(
            "testwwr81@gmail.com ",
            "Obtención",`Se ha obtenido una notificación con el mensaje de "${resultado[0].mensaje}"`
        )
        return result.map(Notificacion.deserialize);  
    }
}

export default Notificacion;