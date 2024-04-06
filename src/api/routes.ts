import { Router } from "express";
import Colaborador from "../entities/colaborador.js";
import Departamento from "../entities/departamento.js";
import Proyecto from "../entities/proyecto.js";
import Tarea from "../entities/tarea.js";
import Notificacion from "../entities/notificacion.js";
import { ForoGeneral, ForoInterno, Mensaje } from "../entities/foro.js";
import Reunion from "../entities/reunion.js";

const apiRoutes = Router();

// Crear colaborador
apiRoutes.post("/colaboradores", async (req, res) => {
    try {
        const colaborador = Colaborador.deserialize(req.body);
        await colaborador.crear();
        return res.json({success: true});
    } catch (error) {
        return res.json({success: false, error: error.message});
    }
});

// Modificar colaborador
apiRoutes.put("/colaboradores/:idColaborador", async (req, res) => {
    const { idColaborador } = req.params;
    const { telefono, idProyecto, idDepartamento, email } = req.body;
    const colaborador = Colaborador.byId(Number(idColaborador));
    await colaborador.actualizar(telefono, idProyecto, idDepartamento, email);
    return res.json({success: true});
});

// Notificaciones de colaborador
apiRoutes.get("/colaboradores/:idColaborador/notificaciones", async (req, res) => {
    const { idColaborador } = req.params;
    const colaborador = Colaborador.byId(Number(idColaborador));
    const notificaciones = await colaborador.obtenerNotificaciones();
    return res.json(notificaciones);
});

// Reasignar a colaborador de proyecto
apiRoutes.put("/colaboradores/:idColaborador/reasignar-proyecto", async (req, res) => {
    const { idColaborador } = req.params;
    const { idProyecto } = req.body;
    const colaborador = Colaborador.byId(Number(idColaborador));
    await colaborador.reasignarProyecto(Proyecto.byId(idProyecto));
    return res.json({success: true});
});

// Tareas de colaborador
apiRoutes.get("/colaboradores/:idColaborador/tareas", async (req, res) => {
    const { idColaborador } = req.params;
    const colaborador = Colaborador.byId(Number(idColaborador));
    const tareas = await colaborador.obtenerTareas();
    return res.json(tareas);
});


// Colaboradores sin proyecto
apiRoutes.get("/colaboradores/sin-proyecto", async(req, res) => {
    const colaboradores = await Colaborador.obtenerColaboradoresSinProyecto()
    return res.json(colaboradores.map(c => c.serialize()));
});

// Validación de credenciales (login)
apiRoutes.post("/credenciales", async (req, res) => {
    const { email, contrasenna } = req.body;
    const colaborador = await Colaborador.validarCredenciales(email, contrasenna);
    return res.json({success: !!colaborador, colaborador: colaborador ? colaborador.serialize() : null});
});

// Departamentos
apiRoutes.get("/departamentos", async (req, res) => {
    const departamentos = await Departamento.obtenerDepartamentos();
    return res.json(departamentos);
});

// Proyectos
apiRoutes.get("/proyectos", async(req, res) => {
    const proyectos = await Proyecto.obtenerProyectos();
    return res.json(proyectos.map(p => p.serialize()));
});

// Crear proyecto
apiRoutes.post("/proyectos", async(req, res) => {
    const data = {...req.body};
    data.tareas = data.tareas.map(Tarea.deserialize);
    data.colaboradores = data.colaboradores.map(Colaborador.byId);
    const proyecto = Proyecto.deserialize(data);
    await proyecto.crear();
    return res.json({success: true});
});

// Modificar proyecto
apiRoutes.put("/proyectos/:idProyecto", async(req, res) => {
    const { idProyecto } = req.params;
    const tareas = req.body.tareas.map(t => Tarea.deserialize({...t, idProyecto}));
    const colaboradores = req.body.colaboradores.map(c => Colaborador.byId(c));
    const proyecto = Proyecto.deserialize({...req.body, tareas, colaboradores, id: idProyecto});
    await proyecto.actualizar();
    return res.json({success: true});
});

// Tareas de proyecto
apiRoutes.get("/proyectos/:idProyecto/tareas", async(req, res) => {
    const { idProyecto } = req.params;
    const proyecto = Proyecto.byId(idProyecto);
    const tareas = await proyecto.obtenerTareas();
    return res.json(tareas);
});

// Modificar tarea
apiRoutes.put("/tareas/:idTarea", async(req, res) => {
    const { idTarea } = req.params;
    const tarea = Tarea.deserialize({...req.body, id: idTarea});
    await tarea.actualizar();
    return res.json({success: true});
});

// Modificar estado de tarea
apiRoutes.put("/tareas/:idTarea/estado", async(req, res) => {
    const { idTarea } = req.params;
    const tarea = Tarea.deserialize({...req.body, id: idTarea});
    await tarea.actualizarEstado();
    return res.json({success: true});
});

// Eliminación de tarea
apiRoutes.delete("/tareas/:idTarea", async(req, res) => {
    const { idTarea } = req.params;
    const tarea = Tarea.byId(Number(idTarea));
    await tarea.eliminar();
    return res.json({success: true});
});

// Creación de tarea
apiRoutes.post("/proyectos/:idProyecto/tareas", async(req, res) => {
    const { idProyecto } = req.params;
    const tarea = Tarea.deserialize({...req.body, idProyecto});
    try {
        await tarea.crear();
        return res.json({success: true});
    } catch (error) {
        return res.json({success: false});
    }
});

// Colaboradores de proyecto
apiRoutes.get("/proyectos/:idProyecto/colaboradores", async(req, res) => {
    const { idProyecto } = req.params;
    const proyecto = Proyecto.byId(idProyecto);
    const colaboradores = await proyecto.obtenerColaboradores();
    return res.json(colaboradores.map(c => c.serialize()));
});

// Eliminar colaborador en un proyecto
apiRoutes.delete("/proyectos/:idProyecto/colaboradores/:idColaborador", async(req, res) => {
    const { idProyecto, idColaborador } = req.params;
    const proyecto = Proyecto.byId(Number(idProyecto));
    proyecto.eliminarColaborador(Colaborador.byId(Number(idColaborador)));
    return res.json({success: true});
});

// Crear notificación
apiRoutes.post("/notificaciones", async(req, res) => {
    const notificacion = Notificacion.deserialize(req.body);
    await notificacion.crear();
    return res.json({success: true});
});

// Foro general
apiRoutes.get("/foros/general", async(req, res) => {
    const foro = await ForoGeneral.obtenerForo();
    return res.json(foro);
});

// Foro interno
apiRoutes.get("/foros/:idProyecto", async(req, res) => {
    const { idProyecto } = req.params;
    const proyecto = await Proyecto.loadDataById(Number(idProyecto));
    const foro = await ForoInterno.obtenerForo(proyecto);
    return res.json(foro);
});

// Crear mensaje para foro general
apiRoutes.post("/foros/general/mensajes", async(req, res) => {
    const foro = ForoGeneral.new();
    const mensaje = Mensaje.deserialize(req.body);
    await foro.guardarMensaje(mensaje);
    return res.json({success: true});
});

// Crear mensaje para foro interno
apiRoutes.post("/foros/:idProyecto/mensajes", async(req, res) => {
    const { idProyecto } = req.params;
    const foro = ForoInterno.byId(Number(idProyecto));
    const mensaje = Mensaje.deserialize(req.body);
    await foro.guardarMensaje(mensaje);
    return res.json({success: true});
});

// Crear reunión para proyecto
apiRoutes.post("/proyectos/:idProyecto/reuniones", async(req, res) => {
    const { idProyecto } = req.params;
    const colaboradores = req.body.colaboradores.map(Colaborador.byId);
    const reunion = Reunion.deserialize({...req.body, idProyecto, colaboradores});
    await reunion.crear();
    return res.json({success: true});
});

// Reuniones de proyecto
apiRoutes.get("/proyectos/:idProyecto/reuniones", async(req, res) => {
    const { idProyecto } = req.params;
    const proyecto = Proyecto.byId(idProyecto);
    const reuniones = await proyecto.obtenerReuniones();
    return res.json(reuniones);
});

// Colaboradores de reunión
apiRoutes.get("/reuniones/:idReunion/colaboradores", async(req, res) => {
    const { idReunion } = req.params;
    const reunion = Reunion.byId(idReunion);
    const colaboradores = await reunion.obtenerColaboradores();
    return res.json(colaboradores);
});

// Estados de tarea
apiRoutes.get("/tareas/estados", async(req, res) => {
    return res.json(await Tarea.obtenerEstados());
});

// Estados de proyecto
apiRoutes.get("/proyectos/estados", async(req, res) => {
    return res.json(await Proyecto.obtenerEstados());
});

// Colaboradores
apiRoutes.get("/colaboradores", async(req, res) => {
    const colaboradores = await Colaborador.obtenerColaboradores();
    return res.json(colaboradores);
});

export default apiRoutes;