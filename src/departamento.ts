import databaseQuery from "./database.js";

class Departamento {
    id:number;
    nombre:string;

    constructor(id:number, nombre:string) {
        this.id = id;
        this.nombre = nombre;
    }

    static deserialize({ id, nombre }) {
        return new Departamento(id, nombre);
    }

    static async obtenerDepartamentos() {
        return (await databaseQuery(`SELECT id, nombre FROM Departamentos`)).map(Departamento.deserialize);
    }
}

export default Departamento;