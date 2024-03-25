class Tarea {
    id: number;
	nombre: string;
	storyPoints: number;
	idProyecto: number;
	idEncargado: number;
	fechaInicio: Date;

    constructor(id: number, nombre: string, storyPoints: number, idProyecto: number, idEncargado: number, fechaInicio: Date) {
        this.id = id;
        this.nombre = nombre;
        this.storyPoints = storyPoints;
        this.idProyecto = idProyecto;
        this.idEncargado = idEncargado;
        this.fechaInicio = fechaInicio;
    }

    static deserialize({id, nombre, storyPoints, idProyecto, idEncargado, fechaInicio}) {
        return new Tarea(id, nombre, storyPoints, idProyecto, idEncargado, fechaInicio);
    }
}

export default Tarea;