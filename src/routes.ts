import { Router } from "express";
import Colaborador from "./colaborador.js";
import Departamento from "./departamento.js";
import Proyecto from "./proyecto.js";
import Tarea from "./tarea.js";
import Notificacion from "./notificacion.js";

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

apiRoutes.get("/colaboradores/:idColaborador/notificaciones", async (req, res) => {
    const { idColaborador } = req.params;
    const colaborador = Colaborador.byId(Number(idColaborador));
    const notificaciones = await colaborador.obtenerNotificaciones();
    return res.json(notificaciones);
});

apiRoutes.put("/colaboradores/:idColaborador/reasignar-proyecto", async (req, res) => {
    const { idColaborador } = req.params;
    const { idProyecto } = req.body;
    const colaborador = Colaborador.byId(Number(idColaborador));
    await colaborador.reasignarProyecto(Proyecto.byId(idProyecto));
    return res.json({success: true});
});

apiRoutes.get("/colaboradores/sin-proyecto", async(req, res) => {
    const colaboradores = await Colaborador.obtenerColaboradoresSinProyecto()
    return res.json(colaboradores.map(c => c.serialize()));
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

apiRoutes.get("/proyectos/:idProyecto/colaboradores", async(req, res) => {
    const { idProyecto } = req.params;
    const proyecto = Proyecto.byId(idProyecto);
    const colaboradores = await proyecto.obtenerColaboradores();
    return res.json(colaboradores.map(c => c.serialize()));
});

apiRoutes.post("/notificaciones", async(req, res) => {
    const notificacion = Notificacion.deserialize(req.body);
    await notificacion.crear();
    return res.json({success: true});
});

export default apiRoutes;