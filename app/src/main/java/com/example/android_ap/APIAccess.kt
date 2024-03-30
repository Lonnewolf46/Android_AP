package com.example.android_ap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.annotations.SerializedName
import android.util.Log
import retrofit2.http.Body
import retrofit2.http.POST

interface iAPIAccess {
    @POST("credenciales")
    fun postAPIlogin(@Body body: Map<String, String>): Call<APIlogin>
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

class APIAccess {

    fun postAPIlogin(email:String,contrasenna:String,onSuccess: (APIlogin) -> Unit) {
        val api = Retrofit.Builder()
            .baseUrl("https://apiap-production.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(iAPIAccess::class.java)
        val body = mapOf(
            "email" to email,
            "contrasenna" to contrasenna
        )
        api.postAPIlogin(body).enqueue(object : Callback<APIlogin> {
            override fun onResponse(call: Call<APIlogin>, response: Response<APIlogin>) {
                response.body()?.let {
                    onSuccess(it);
                }
            }
            override fun onFailure(call: Call<APIlogin>, t: Throwable) {}
            })
        }
}


