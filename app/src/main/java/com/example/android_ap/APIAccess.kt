import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface iAPIAccess {
    @POST("credenciales")
    suspend fun postAPIlogin(@Body body: Map<String, String>): APIlogin
}

class APIAccess {

    suspend fun postAPIlogin(email: String, contrasenna: String): APIlogin {
        val api = Retrofit.Builder()
            .baseUrl("https://apiap-production.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(iAPIAccess::class.java)

        val body = mapOf(
            "email" to email,
            "contrasenna" to contrasenna
        )

        return api.postAPIlogin(body)
    }
}

data class User (
    var id: Int,
    var nombre: String,
    var email: String,
    var cedula: Int,
    var telefono: Int

)

data class APIlogin(
    val success:Boolean,
    var colaborador: User
)




