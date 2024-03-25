import databaseQuery from "./database.js";

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
                SELECT id, nombre, cedula, telefono, email, idProyecto, idDepartamento
                FROM Colaboradores
                WHERE email='${email}' and contrasenna='${contrasenna}'
            `).then(result => {
                resolve(result.length ? Colaborador.deserialize(result[0]) : null);
            });
        });
    }

    serialize() {
        return {
            id: this.id, nombre: this.nombre, cedula: this.cedula, telefono: this.telefono,
            email: this.email, idProyecto: this.idProyecto, idDepartamento: this.idDepartamento
        }
    }

    async crear() {
        await databaseQuery(`
            INSERT INTO Colaboradores(nombre, cedula, telefono, email, contrasenna, idProyecto, idDepartamento)
            VALUES('${this.nombre}', ${this.cedula}, ${this.telefono}, '${this.email}', '${this.contrasenna}', ${this.idProyecto}, ${this.idDepartamento})
        `);
    }

    async actualizar(telefono:number, idProyecto:number, idDepartamento:number, email:string) {
        await databaseQuery(`
            UPDATE Colaboradores SET
            telefono=${telefono}, idProyecto=${idProyecto}, idDepartamento=${idDepartamento}, email='${email}'
            WHERE id=${this.id}
        `);
    }

    async reasignarProyecto(idProyecto:number) {
        await databaseQuery(`UPDATE Colaboradores SET idProyecto=${idProyecto} WHERE id=${this.id}`);
    }
}

export default Colaborador;