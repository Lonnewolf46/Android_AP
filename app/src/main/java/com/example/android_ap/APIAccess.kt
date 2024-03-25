package com.example.android_ap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.annotations.SerializedName
import android.util.Log
interface APIAccess {
    @GET("posts/1")
    fun getPost(): Call<Post>
}

data class Post (
    val userId: Int,
    var id: Int,
    val title: String,
    val body: String
)

class APICall {
    fun getPost(onSuccess: (Post) -> Unit) {
        val api = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIAccess::class.java)

        api.getPost().enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                response.body()?.let {
                    onSuccess(it);
                }
            }
            override fun onFailure(call: Call<Post>, t: Throwable) {}
            })
        }
}


