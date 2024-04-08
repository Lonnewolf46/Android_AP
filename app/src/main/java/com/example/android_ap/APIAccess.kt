import com.example.android_ap.APIlogin
import com.example.android_ap.Colaborador
import com.example.android_ap.Depto
import com.example.android_ap.Estado
import com.example.android_ap.ForoGeneral
import com.example.android_ap.ForoProyecto
import com.example.android_ap.Notificacion
import com.example.android_ap.Proyecto
import com.example.android_ap.ProyectoEnviar
import com.example.android_ap.Response
import com.example.android_ap.Reunion
import com.example.android_ap.Tarea
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Rutas {
    @POST("credenciales")
    suspend fun postAPIlogin(@Body body: Map<String, String>): APIlogin

    @GET("departamentos")
    suspend fun getAPIDeptos(): List<Depto>

    @GET("proyectos")
    suspend fun getAPIProyectos(): List<Proyecto>

    @GET("colaboradores")
    suspend fun getAPIColaboradores(): List<Colaborador>

    @GET("proyectos/estados")
    suspend fun getAPIProyectoEstados(): List<Estado>

    @POST("colaboradores")
    suspend fun postAPICrearColaborador(@Body body: Map<String, String>): Response

    @PUT("colaboradores/{id}")
    suspend fun putAPIModificarColaborador(@Path("id") id: Int, @Body body: Map<String, String>): Response

    @DELETE("proyectos/{id}/colaboradores/{id}")
    suspend fun deleteAPIEliminarColaborador(@Path("id") idProyecto: Int,@Path("id") idColaborador: Int): Response

    @PUT("colaboradores/{id}/reasignar-proyecto")
    suspend fun putAPICambiarProyectoColaborador(@Path("id") id: Int, @Body body: Map<String, Int>):Response

    @GET("notificaciones/{idProyecto}")
    suspend fun getAPINotificaciones(@Path("idProyecto") idProyecto: Int): List<Notificacion>

    @GET("proyectos/{id}/tareas")
    suspend fun getAPITareasProyecto(@Path("id") id: Int):List<Tarea>

    @GET("colaboradores/{id}/tareas")
    suspend fun getAPITareasColaborador(@Path("id") id: Int): List<Tarea>

    @GET("proyectos/{id}/colaboradores")
    suspend fun getAPIColaboradores(@Path("id") id: Int): List<Colaborador>

    @GET("tareas/estados")
    suspend fun getAPITareaEstados(): List<Estado>

    @POST("proyectos/{id}/tareas")
    suspend fun postAPINuevaTarea(@Path("id") id: Int, @Body body: Map<String, String>): Response

    @PUT("tareas/{id}")
    suspend fun putAPIModificarTarea(@Path("id") id: Int, @Body body: Map<String, String>): Response

    @DELETE("tareas/{id}")
    suspend fun deleteAPIEliminarTarea(@Path("id") id: Int): Response

    @POST("proyectos/{id}/reuniones")
    suspend fun postAPICrearReunion(@Path("id") id: Int, @Body body: Reunion): Response

    @GET("foros/general")
    suspend fun getAPIMensajeForoGen():ForoGeneral

    @POST("foros/general/mensajes")
    suspend fun putAPIMensajeForoGen(@Body body: Map<String, String>): Response

    @GET("foros/{id}")
    suspend fun getAPIMensajeForoPro(@Path("id") id: Int): ForoProyecto

    @POST("foros/{id}/mensajes")
    suspend fun putAPIMensajeForoPro(@Path("id") id: Int ,@Body body: Map<String, String>): Response

    @POST("proyectos")
    suspend fun postAPICrearProyecto(@Body body: ProyectoEnviar): Response

    @PUT("proyectos/{id}")
    suspend fun putAPIModificarProyecto(@Path("id") id: Int, @Body body: ProyectoEnviar): Response

}

class APIAccess {
    suspend fun postAPIlogin(email: String, contrasenna: String): APIlogin {
        val api = api()

        val body = mapOf(
            "email" to email,
            "contrasenna" to contrasenna
        )

        return api.postAPIlogin(body)
    }

    suspend fun getAPIDeptos(): List<Depto> {
        val api = api()
        return api.getAPIDeptos()
    }

    suspend fun getAPIProyectos(): List<Proyecto>{
        val api = api()
        return api.getAPIProyectos()
    }

    suspend fun getAPIProyectoEstados(): List<Estado>{
        val api = api()
        return api.getAPIProyectoEstados()
    }

    suspend fun getAPIColaboradores(): List<Colaborador>{
        val api = api()
        return api.getAPIColaboradores()
    }

    suspend fun postAPICrearColaborador(
        nombre: String,
        cedula: Int,
        telefono: Int,
        email: String,
        contrasenna: String,
        idProyecto: Int,
        idDepartamento: Int): Response
    {
        val api = api()
        val body = mapOf(
            "nombre" to nombre,
            "cedula" to cedula.toString(),
            "telefono" to telefono.toString(),
            "email" to email,
            "contrasenna" to contrasenna,
            "idProyecto" to idProyecto.toString(),
            "idDepartamento" to idDepartamento.toString()
        )
        return api.postAPICrearColaborador(body)
    }

    suspend fun putAPIModificarColaborador(
        id: Int,
        telefono: Int,
        email: String,
        idProyecto: Int,
        idDepartamento: Int): Response
    {
        val api = api()
        val body = mapOf(
            "telefono" to telefono.toString(),
            "email" to email,
            "idProyecto" to idProyecto.toString(),
            "idDepartamento" to idDepartamento.toString()
        )
        return api.putAPIModificarColaborador(id,body)
    }

    suspend fun deleteAPIEliminarColaborador(idProyecto: Int, idColaborador: Int): Response
    {
        val api = api()
        return api.deleteAPIEliminarColaborador(idProyecto, idColaborador)
    }

    suspend fun putAPICambiarProyectoColaborador(id: Int, idProyecto: Int):Response{
        val api = api()
        val body = mapOf(
            "idProyecto" to idProyecto
        )
        return api.putAPICambiarProyectoColaborador(id, body)
    }

    suspend fun getAPINotificaciones(id: Int): List<Notificacion>{
        val api = api()
        return api.getAPINotificaciones(id)
    }

    suspend fun getAPITareasProyecto(id: Int):List<Tarea>{
        val api = api()
        return api.getAPITareasProyecto(id)
    }

    suspend fun getAPITareasColaborador(id: Int): List<Tarea>{
        val api = api()
        return api.getAPITareasColaborador(id)
    }

    suspend fun getAPIColaboradores(id: Int): List<Colaborador>{
        val api = api()
        return api.getAPIColaboradores(id)
    }

    suspend fun getAPIEstados(): List<Estado>{
        val api = api()
        return api.getAPITareaEstados()
    }

    suspend fun postAPINuevaTarea(
        idProyecto: Int,
        nombre: String,
        storyPoints: String,
        idEncargado: Int,
        fechaInicio: String,
        fechaFin: String,
        idEstado: Int): Response
    {
        val api = api()
        val body = mapOf(
            "nombre" to nombre,
            "storyPoints" to storyPoints,
            "idEncargado" to idEncargado.toString(),
            "fechaInicio" to fechaInicio,
            "fechaFin" to fechaFin,
            "idEstado" to idEstado.toString()
        )
        return api.postAPINuevaTarea(idProyecto,body)
    }

    suspend fun putAPIModificarTarea(
        id: Int,
        nombre: String,
        storyPoints: String,
        idEncargado: Int,
        fechaFin: String,
        idEstado: Int): Response
    {
        val api = api()
        val body = mapOf(
            "nombre" to nombre,
            "storyPoints" to storyPoints,
            "idEncargado" to idEncargado.toString(),
            "fechaFin" to fechaFin,
            "idEstado" to idEstado.toString()
        )
        return api.putAPIModificarTarea(id, body)
    }

    suspend fun deleteAPIEliminarTarea(id: Int): Response{
        val api = api()
        return api.deleteAPIEliminarTarea(id)
    }

    suspend fun postAPICrearReunion(idProyecto: Int, reunion: Reunion): Response{
        val api = api()
        return api.postAPICrearReunion(idProyecto, reunion)
    }

    suspend fun getAPIMensajeForoGen(): ForoGeneral{
        val api = api()
        return api.getAPIMensajeForoGen()
    }

    suspend fun postAPIMensajeForoGen(mensaje: String, idEmisor: Int): Response{
        val api = api()
        val body = mapOf(
            "mensaje" to mensaje,
            "idEmisor" to idEmisor.toString()
        )
        return api.putAPIMensajeForoGen(body)
    }

    suspend fun getAPIMensajeForoPro(id: Int): ForoProyecto{
        val api = api()
        return api.getAPIMensajeForoPro(id)
    }

    suspend fun postAPIMensajeForoPro(
        idProyecto: Int,
        mensaje: String,
        idEmisor: Int): Response
    {
        val api = api()
        val body = mapOf(
            "mensaje" to mensaje,
            "idEmisor" to idEmisor.toString()
        )
        return api.putAPIMensajeForoPro(idProyecto,body)
    }

    suspend fun postAPICrearProyecto(body: ProyectoEnviar): Response{
        val api = api()
        return api.postAPICrearProyecto(body)
    }

    suspend fun putAPIModificarProyecto(id: Int,body: ProyectoEnviar): Response
    {
        val api = api()
        return api.putAPIModificarProyecto(id, body)
    }
}

fun api():Rutas{
   return Retrofit.Builder()
        .baseUrl("https://apiap-production.up.railway.app/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Rutas::class.java)
}




