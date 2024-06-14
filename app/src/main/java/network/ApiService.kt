package network

import retrofit2.Call
import retrofit2.http.GET
import model.Item

interface ApiService {
    @GET("hiring.json")
    fun fetchItems(): Call<List<Item>>
}