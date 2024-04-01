import Colaborador from "./colaborador.js";
import databaseQuery from "../services/database.js";
import sendMail from "../services/mail.js";

class Reunion {
    id:number;
    fecha:Date;
    tema:string;
    medio:string;
    formato:string;
    enlace:string;
    idCreador:number;
    idProyecto:number;
    colaboradores:Colaborador[];

    constructor(id:number, fecha:Date, tema:string, medio:string, formato:string, enlace:string, idCreador:number, idProyecto:number, colaboradores:Colaborador[]) {
        this.id = id;
        this.fecha = fecha;
        this.tema = tema;
        this.medio = medio;
        this.formato = formato;
        this.enlace = enlace;
        this.idCreador = idCreador;
        this.idProyecto = idProyecto;
        this.colaboradores = colaboradores;
    }

    static byId(id:number) {
        return new Reunion(id, new Date(), "", "", "", "", 0, 0, []);
    }

    static deserialize({id, fecha, tema, medio, formato, enlace, idCreador, idProyecto, colaboradores}) {
        return new Reunion(id, fecha, tema, medio, formato, enlace, idCreador, idProyecto, colaboradores);
    }

    async crear() {
        const result = await databaseQuery(`
            INSERT INTO Reuniones (fecha, tema, medio, formato, enlace, idCreador, idProyecto)
            VALUES ('${this.fecha}', '${this.tema}', '${this.medio}', '${this.formato}', '${this.enlace}', ${this.idCreador}, ${this.idProyecto})

            SELECT SCOPE_IDENTITY() as idReunion
        `);
        this.id = result[0].idReunion;
        if(this.colaboradores.length){
            await databaseQuery(`
                INSERT INTO AsistentesReunion (idAsistente, idReunion) VALUES
               ${this.colaboradores.map(colaborador => `(${colaborador.id}, ${this.id})`).join(",")}
            `);
            const colaboradores = await this.obtenerColaboradores();
            for(let i=0; i<colaboradores.length; i++) {
                const colaborador = colaboradores[i];
                await sendMail(
                    colaborador.email,
                    "Nueva reunión", `
Se le ha invitado a una reunión con formato ${this.formato}.
Tema: ${this.tema}.
Con fecha: ${this.fecha}.
Medio: ${this.medio}.
Enlace/Unicación: ${this.enlace}.
` );
            }
        }
    }

    async obtenerColaboradores():Promise<Colaborador[]> {
        const result = await databaseQuery(`
            SELECT c.id, c.nombre, c.cedula, c.telefono, c.email, c.idProyecto, c.idDepartamento
            FROM Colaboradores c, AsistentesReunion a
            WHERE a.idReunion=${this.id} and c.id=a.idAsistente
        `);
        return result.map(Colaborador.deserialize);
    }
}

export default Reunion;