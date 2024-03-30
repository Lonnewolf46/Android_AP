import { Router } from "express";
import Colaborador from "./colaborador.js";
import Departamento from "./departamento.js";
import Proyecto from "./proyecto.js";
import Tarea from "./tarea.js";
import Notificacion from "./notificacion.js";
import Foro, { ForoGeneral, ForoInterno, Mensaje } from "./foro.js";
import Reunion from "./reunion.js";

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
    return res.json({success: !!colaborador, colaborador: colaborador ? colaborador.serialize() : null});
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

apiRoutes.put("/proyectos/:idProyecto", async(req, res) => {
    const { idProyecto } = req.params;
    const tareas = req.body.tareas.map(t => Tarea.deserialize({...t, idProyecto}));
    const colaboradores = req.body.colaboradores.map(c => Colaborador.byId(c));
    const proyecto = Proyecto.deserialize({...req.body, tareas, colaboradores, id: idProyecto});
    await proyecto.actualizar();
    return res.json({success: true});
});

apiRoutes.get("/proyectos/:idProyecto/tareas", async(req, res) => {
    const { idProyecto } = req.params;
    const proyecto = Proyecto.byId(idProyecto);
    const tareas = await proyecto.obtenerTareas();
    return res.json(tareas);
});

// Actualizar tarea
apiRoutes.put("/proyectos/tareas/actualizar", async(req, res) => {
    const tarea = Tarea.deserialize({...req.body});
    await tarea.actualizar();
    return res.json({success: true});
});

// Actualizar estado tarea
apiRoutes.put("/proyectos/tareas/estado", async(req, res) => {
    const tarea = Tarea.deserialize({...req.body});
    await tarea.actualizarEstado();
    return res.json({success: true});
});

apiRoutes.delete("/proyectos/:idProyecto/tareas/:idTarea", async(req, res) => {
    const { idProyecto, idTarea } = req.params;
    const proyecto = Proyecto.byId(Number(idProyecto));
    await proyecto.eliminarTarea(Tarea.byId(Number(idTarea)));
    return res.json({success: true});
});

apiRoutes.post("/proyectos/tareas", async(req, res) => {
    const tarea = Tarea.deserialize({...req.body});
    await tarea.crear();
    return res.json({success: true});
});

apiRoutes.get("/proyectos/:idProyecto/colaboradores", async(req, res) => {
    const { idProyecto } = req.params;
    const proyecto = Proyecto.byId(idProyecto);
    const colaboradores = await proyecto.obtenerColaboradores();
    return res.json(colaboradores.map(c => c.serialize()));
});

apiRoutes.delete("/proyectos/:idProyecto/colaboradores/:idColaborador", async(req, res) => {
    const { idProyecto, idColaborador } = req.params;
    const proyecto = Proyecto.byId(Number(idProyecto));
    proyecto.eliminarColaborador(Colaborador.byId(Number(idColaborador)));
    return res.json({success: true});
});

apiRoutes.post("/notificaciones", async(req, res) => {
    const notificacion = Notificacion.deserialize(req.body);
    await notificacion.crear();
    return res.json({success: true});
});

apiRoutes.get("/foros/general", async(req, res) => {
    const foro = await ForoGeneral.obtenerForo();
    return res.json(foro);
});

apiRoutes.get("/foros/:idProyecto", async(req, res) => {
    const { idProyecto } = req.params;
    const proyecto = await Proyecto.loadDataById(Number(idProyecto));
    const foro = await ForoInterno.obtenerForo(proyecto);
    return res.json(foro);
});

apiRoutes.post("/foros/general/mensajes", async(req, res) => {
    const foro = ForoGeneral.new();
    const mensaje = Mensaje.deserialize(req.body);
    await foro.guardarMensaje(mensaje);
    return res.json({success: true});
});

apiRoutes.post("/foros/:idProyecto/mensajes", async(req, res) => {
    const { idProyecto } = req.params;
    const foro = ForoInterno.byId(Number(idProyecto));
    const mensaje = Mensaje.deserialize(req.body);
    await foro.guardarMensaje(mensaje);
    return res.json({success: true});
});

apiRoutes.post("/proyectos/:idProyecto/reuniones", async(req, res) => {
    const { idProyecto } = req.params;
    const colaboradores = req.body.colaboradores.map(Colaborador.byId);
    const reunion = Reunion.deserialize({...req.body, idProyecto, colaboradores});
    await reunion.crear();
    return res.json({success: true});
});

apiRoutes.get("/proyectos/:idProyecto/reuniones", async(req, res) => {
    const { idProyecto } = req.params;
    const proyecto = Proyecto.byId(idProyecto);
    const reuniones = await proyecto.obtenerReuniones();
    return res.json(reuniones);
});

apiRoutes.get("/reuniones/:idReunion/colaboradores", async(req, res) => {
    const { idReunion } = req.params;
    const reunion = Reunion.byId(idReunion);
    const colaboradores = await reunion.obtenerColaboradores();
    return res.json(colaboradores);
});

export default apiRoutes;