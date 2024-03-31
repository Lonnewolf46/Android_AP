import databaseQuery from "./database.js";
import Proyecto from "./proyecto.js";
import Notificacion from "./notificacion.js";
import Departamento from "./departamento.js";
import Tarea from "./tarea.js";

class Colaborador {
    id:number;
    nombre:string;
    cedula:number;
    telefono:number;
    email:string;
    contrasenna:string;
    idProyecto:number;
    idDepartamento:number;
    departamento:Departamento;
    proyecto:Proyecto;
    
    constructor(
        id:number, nombre:string, cedula:number, telefono:number, email:string, contrasenna:string,
        idProyecto:number, idDepartamento:number, departamento:Departamento, proyecto:Proyecto
    ) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
        this.contrasenna = contrasenna;
        this.idProyecto = idProyecto;
        this.idDepartamento = idDepartamento;
        this.departamento = departamento;
        this.proyecto = proyecto;
    }

    static byId(id:number) {
        return new Colaborador(id, "", 0, 0, "", "", 0, 0, null, null);
    }

    static byData(nombre:string, cedula:number, telefono:number, email:string, contrasenna:string, idProyecto:number, idDepartamento:number) {
        return new Colaborador(0, nombre, cedula, telefono, email, contrasenna, idProyecto, idDepartamento, null, null);
    }

    static deserialize({id, nombre, cedula, telefono, email, contrasenna, idProyecto, idDepartamento, departamento, proyecto}) {
        return new Colaborador(id, nombre, cedula, telefono, email, contrasenna, idProyecto, idDepartamento, departamento, proyecto);
    }

    static async validarCredenciales(email:string, contrasenna:string): Promise<Colaborador> {
        return new Promise(resolve => {
            // EXEC ValidarCredenciales @Email = '${email}', @Contrasenna = '${contrasenna}';
            databaseQuery(`
                SELECT
                    c.id, c.nombre, c.cedula, c.telefono, c.email, c.idProyecto, c.idDepartamento,
                    d.nombre as 'nombreDepartamento',
                    p.nombre as 'nombreProyecto', p.recursos, p.presupuesto, p.idEstado,
                    p.descripcion, p.idResponsable, p.fechaInicio, p.fechaFin
                FROM Colaboradores c
                JOIN Departamentos d ON d.id=c.idDepartamento
                JOIN Proyectos p ON c.idProyecto=p.id
                WHERE c.email='${email}' AND c.contrasenna='${contrasenna}'
            `).then(result => {
                if(result.length) {
                    const [data] = result;
                    const colaborador = Colaborador.deserialize(data);
                    const proyecto = Proyecto.deserialize({...data, id: data.idProyecto, nombre: data.nombreProyecto});
                    const departamento = new Departamento(data.idDepartamento, data.nombreDepartamento);
                    colaborador.proyecto = proyecto;
                    colaborador.departamento = departamento;
                    resolve(colaborador);
                } else resolve(null);
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
            email: this.email, idProyecto: this.idProyecto, idDepartamento: this.idDepartamento,
            proyecto: this.proyecto.serialize(), departamento: this.departamento
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

    async obtenerTareas():Promise<Tarea[]> {
        const result = await databaseQuery(`
            SELECT nombre, storyPoints, idProyecto, idEncargado, fechaInicio, fechaFin, idEstado
            FROM Tareas
            WHERE idEncargado=${this.id}
        `);
        return result.map(Tarea.deserialize);
    }
}

export default Colaborador;