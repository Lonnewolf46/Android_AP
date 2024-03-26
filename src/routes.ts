import { Router } from "express";
import Colaborador from "./colaborador.js";
import Departamento from "./departamento.js";
import Proyecto from "./proyecto.js";
import Tarea from "./tarea.js";

const apiRoutes = Router();

apiRoutes.post("/colaboradores", async (req, res) => {
    const colaborador = Colaborador.deserialize(req.body);
    await colaborador.crear();
    return res.json({success: true});
});

apiRoutes.put("/colaboradores/:idColaborador", async (req, res) => {
    const { idColaborador } = req.params;
    const { telefono, idProyecto, idDepartamento, email } = req.body;
    const colaborador = Colaborador.byId(Number(idColaborador));
    await colaborador.actualizar(telefono, idProyecto, idDepartamento, email);
    return res.json({success: true});
});

apiRoutes.put("/colaboradores/:idColaborador/reasignar-proyecto", async (req, res) => {
    const { idColaborador } = req.params;
    const { idProyecto } = req.body;
    const colaborador = Colaborador.byId(Number(idColaborador));
    await colaborador.reasignarProyecto(idProyecto);
    return res.json({success: true});
});

apiRoutes.post("/credenciales", async (req, res) => {
    const { email, contrasenna } = req.body;
    const colaborador = await Colaborador.validarCredenciales(email, contrasenna);
    return res.json({success: !!colaborador, colaborador: colaborador.serialize()});
});

apiRoutes.get("/departamentos", async (req, res) => {
    const departamentos = await Departamento.obtenerDepartamentos();
    return res.json(departamentos);
});

apiRoutes.get("/proyectos", async(req, res) => {
    const proyectos = await Proyecto.obtenerProyectos();
    return res.json(proyectos.map(p => p.serialize()));
});

apiRoutes.post("/proyectos", async(req, res) => {
    const data = {...req.body};
    data.tareas = data.tareas.map(Tarea.deserialize);
    data.colaboradores = data.colaboradores.map(Colaborador.byId);
    const proyecto = Proyecto.deserialize(data);
    await proyecto.crear();
    return res.json({success: true});
});

export default apiRoutes;