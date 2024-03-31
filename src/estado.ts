class Estado {
    id:number;
    estado: string;

    constructor(id, estado){
        this.id = id;
        this.estado = estado;
    }

    static deserialize({id, estado}) {
        return new Estado(id, estado);
    }
}

export default Estado;