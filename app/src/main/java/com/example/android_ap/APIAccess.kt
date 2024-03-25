package com.example.android_ap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.annotations.SerializedName
interface APIAccess {
    @GET("tasks") // Cambia la URL a "tasks"
    fun getTasks(): Call<List<Task>> // Cambia el tipo de retorno a List<Task
}
data class Task (
    val id: Int,
    val title: String,
    val body: String,
    @SerializedName("due_date") val dueDate: String,
    val status: String,
    val userId: Int
)
class APICall {
    fun getTasks(onSuccess: (List<Task>) -> Unit) {
        val api = Retrofit.Builder()
            .baseUrl("http://localhost:5002/api/")  // Ajusta la URL base a tu localhost
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIAccess::class.java)

        api.getTasks().enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                response.body()?.let {
                    onSuccess(it)
                }
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                // Maneja el fallo de la llamada a la API aquí
            }
        })
    }
}