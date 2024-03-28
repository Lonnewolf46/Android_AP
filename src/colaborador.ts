import databaseQuery from "./database.js";
import Proyecto from "./proyecto.js";
import Notificacion from "./notificacion.js";

class Colaborador {
    id:number;
    nombre:string;
    cedula:number;
    telefono:number;
    email:string;
    contrasenna:string;
    idProyecto:number;
    idDepartamento:number;
    
    constructor(id:number, nombre:string, cedula:number, telefono:number, email:string, contrasenna:string, idProyecto:number, idDepartamento:number) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
        this.contrasenna = contrasenna;
        this.idProyecto = idProyecto;
        this.idDepartamento = idDepartamento;
    }

    static byId(id:number) {
        return new Colaborador(id, "", 0, 0, "", "", 0, 0);
    }

    static byData(nombre:string, cedula:number, telefono:number, email:string, contrasenna:string, idProyecto:number, idDepartamento:number) {
        return new Colaborador(0, nombre, cedula, telefono, email, contrasenna, idProyecto, idDepartamento);
    }

    static deserialize({id, nombre, cedula, telefono, email, contrasenna, idProyecto, idDepartamento}) {
        return new Colaborador(id, nombre, cedula, telefono, email, contrasenna, idProyecto, idDepartamento);
    }

    static async validarCredenciales(email:string, contrasenna:string): Promise<Colaborador> {
        return new Promise(resolve => {
            databaseQuery(`
            EXEC ValidarCredenciales @Email = '${email}', @Contrasenna = '${contrasenna}';
            `).then(result => {
                resolve(result.length ? Colaborador.deserialize(result[0]) : null);
            });
        });
    }

    static async obtenerColaboradoresSinProyecto():Promise<Colaborador[]> {
        const colaboradores = await databaseQuery(`
            EXEC ObtenerColaboradoresSinProyecto;
        `);
        return colaboradores.map(Colaborador.deserialize);
    }

    serialize() {
        return {
            id: this.id, nombre: this.nombre, cedula: this.cedula, telefono: this.telefono,
            email: this.email, idProyecto: this.idProyecto, idDepartamento: this.idDepartamento
        }
    }

    async crear() {
        try {
            await databaseQuery(`
            EXEC CrearColaborador 
                @Nombre = '${this.nombre}',
                @Cedula = ${this.cedula},
                @Telefono = ${this.telefono},
                @Email = '${this.email}',
                @Contrasenna = '${this.contrasenna}',
                @IdProyecto = ${this.idProyecto},
                @IdDepartamento = ${this.idDepartamento};
            `);
        } catch (error) {
            
            console.error(error);
        }
    }

    async actualizar(telefono:number, idProyecto:number, idDepartamento:number, email:string) {
        try {
            await databaseQuery(`
            EXEC ActualizarColaborador 
                @Id = ${this.id},
                @Telefono = ${telefono},
                @IdProyecto = ${idProyecto},
                @IdDepartamento = ${idDepartamento},
                @Email = '${email}';
            `);
        } catch (error) {
            console.error(error);
        }
    }

    async reasignarProyecto(proyecto:Proyecto) {
        try {
            await databaseQuery(`
            EXEC ReasignarProyectoColaborador 
                @Id = ${this.id}, 
                @IdProyecto = ${proyecto.id}; 
            `);
        } catch (error) {
            console.error(error);
        }
    }

    async obtenerNotificaciones():Promise<Notificacion[]> {
        const result = await databaseQuery(`
        EXEC ObtenerNotificaciones @IdColaborador = ${this.id}; 
        `);
        return result.map(Notificacion.deserialize);
    }
}

export default Colaborador;