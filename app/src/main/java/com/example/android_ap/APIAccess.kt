import com.example.android_ap.APIlogin
import com.example.android_ap.Depto
import com.example.android_ap.Project
import com.example.android_ap.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
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
    suspend fun getAPIProyectos(): List<Project>

    @POST("colaboradores")
    suspend fun postAPICrearColaborador(@Body body: Map<String, String>): Response

    @PUT("colaboradores/{id}")
    suspend fun putAPIModificarColaborador(@Path("id") id: Int, @Body body: Map<String, String>): Response

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

    suspend fun getAPIProyectos(): List<Project>{
        val api = api()
        return api.getAPIProyectos()
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

}

fun api():Rutas{
   return Retrofit.Builder()
        .baseUrl("https://apiap-production.up.railway.app/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Rutas::class.java)
}




